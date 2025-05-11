package fr.dl11.openmedia.datasource.mappers;

import fr.dl11.openmedia.common.utils.WithDefault;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.exceptions.ElementParsingException;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.IElementParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of the {@link IDataMapper} interface.
 *
 * <p>The {@code DefaultDataMapper} class provides functionality to map data records
 * to objects of a specified class type. It uses annotations to bind fields in the
 * class to keys in the data record and supports custom parsers for field values.
 *
 * @param <T> The type of object to map the data to.
 */
public class DefaultDataMapper<T> implements IDataMapper<T> {

    /**
     * The class type to map the data to.
     */
    private final Class<T> clazz;

    /**
     * A map of field names (keys) to their corresponding {@link Field} objects in the class.
     */
    private final Map<String, Field> annotatedFields;

    /**
     * A map of field names (keys) to their corresponding element parsers.
     */
    private final Map<String, IElementParser<?>> elementParsers;

    /**
     * Creates an instance of an {@link EnumParser} for a given enum class.
     *
     * @param enumClass The enum class for which the parser is created.
     * @param <E>       The type of the enum.
     * @return An instance of {@link EnumParser}.
     * @throws IllegalArgumentException If the provided class is not an enum type.
     */
    @SuppressWarnings("unchecked")
    private <E extends Enum<E>> IElementParser<E> createEnumParserInstance(Class<?> enumClass) {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("Type " + enumClass.getName() + " is not an Enum type.");
        }

        Class<E> specificEnumClass = (Class<E>) enumClass;
        E defaultValue = null;

        if (WithDefault.class.isAssignableFrom(specificEnumClass)) {
            E[] enumConstants = specificEnumClass.getEnumConstants();
            if (enumConstants != null && enumConstants.length > 0) {
                try {
                    WithDefault<E> defaultProvider = (WithDefault<E>) enumConstants[0];
                    defaultValue = defaultProvider.getDefault();
                } catch (ClassCastException e) {
                    System.err.println("Warning: Enum " + specificEnumClass.getSimpleName() +
                            " seems to implement WithDefault incorrectly. Using null default. Error: " + e.getMessage());
                }
            } else {
                System.err.println("Warning: Enum " + specificEnumClass.getSimpleName() +
                        " implements WithDefault but has no constants. Using null default.");
            }
        } else {
            System.out.println("Info: Enum " + specificEnumClass.getSimpleName() +
                    " does not implement WithDefault. EnumParser will use its internal null default.");
        }

        try {
            return new EnumParser<>(specificEnumClass, defaultValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate EnumParser for " + specificEnumClass.getName(), e);
        }
    }

    /**
     * Constructs a new {@code DefaultDataMapper} for the specified class type.
     *
     * @param clazz The class type to map the data to.
     */
    @SuppressWarnings("rawtypes")
    public DefaultDataMapper(Class<T> clazz) {
        this.clazz = clazz;
        annotatedFields = new HashMap<>();
        elementParsers = new HashMap<>();

        // Initialize annotatedFields based on the class annotations
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> {
                    field.setAccessible(true);
                    return field.isAnnotationPresent(BindToKey.class);
                })
                .forEach(field -> {
                    BindToKey bindToKey = field.getAnnotation(BindToKey.class);
                    assert bindToKey != null;
                    String fieldKey = bindToKey.key().toLowerCase();
                    Class<? extends IElementParser> parserClass = bindToKey.parser();
                    Class<?> fieldType = field.getType();
                    IElementParser<?> parserInstance;

                    try {
                        if (parserClass == EnumParser.class) {
                            if (!fieldType.isEnum()) {
                                throw new IllegalArgumentException("Field '" + field.getName() + "' in class '" + this.clazz.getSimpleName() +
                                        "' is annotated with EnumParser but is not an Enum type.");
                            }
                            parserInstance = createEnumParserInstance(fieldType);

                        } else {
                            parserInstance = parserClass.getDeclaredConstructor().newInstance();
                        }
                        elementParsers.put(fieldKey, parserInstance);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException("Parser class " + parserClass.getName() +
                                " for field '" + field.getName() + "' in class '" + this.clazz.getSimpleName() +
                                "' is missing a no-argument constructor.");
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("Failed to instantiate parser '" + parserClass.getName() +
                                "' for field '" + field.getName() + "' in class '" + this.clazz.getSimpleName() + "'.");
                    }

                    this.annotatedFields.put(fieldKey, field);
                });
    }

    /**
     * Checks if the given {@link DataRecord} can be mapped to an object of type {@code T}.
     *
     * @param record The data record to check.
     * @return {@code true} if the record can be mapped, {@code false} otherwise.
     */
    @Override
    public boolean canMap(DataRecord record) {
        return record != null && record.fields().size() == annotatedFields.size() &&
                record.fields().stream().allMatch(field -> annotatedFields.containsKey(field.name().toLowerCase()));
    }

    /**
     * Maps the given {@link DataRecord} to an object of type {@code T}.
     *
     * @param record The data record to map.
     * @return An object of type {@code T} mapped from the data record.
     * @throws MatchException If the mapping fails.
     */
    @Override
    public T mapData(DataRecord record) throws MatchException {
        assert canMap(record);

        // Create an instance of the class using reflection
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T instance = constructor.newInstance();

            for (var field : record.fields()) {
                String fieldName = field.name().toLowerCase();
                assert annotatedFields.containsKey(fieldName);

                Field classField = annotatedFields.get(fieldName);
                IElementParser<?> parser = elementParsers.get(fieldName);
                assert parser != null;

                Object parsedValue = parser.parse(field.data());
                // Set the value of the field
                classField.set(instance, parsedValue);

            }

            return instance;
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Failed to create instance of class " + e.getClass().getName() + ": No default constructor with no args found !");
        } catch (ElementParsingException e) {
            throw new MatchException("Failed to parse element: " + e.getMessage(), e.getCause());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field value: " + e.getMessage(), e);
        }
    }
}