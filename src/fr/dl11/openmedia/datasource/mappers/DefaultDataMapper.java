package fr.dl11.openmedia.datasource.mappers;

import fr.dl11.openmedia.common.utils.WithDefault;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.exceptions.ElementParsingException;
import fr.dl11.openmedia.datasource.parsers.IDataParser;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.IElementParser;
import fr.dl11.openmedia.types.DataType;

import java.io.InvalidObjectException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DefaultDataMapper<T> implements IDataMapper<T> {
    private final Class<T> clazz;
    private final Map<String, Field> annotatedFields;
    private final Map<String, IElementParser<?>> elementParsers;

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
        } catch (Exception e) { // Catch whatever EnumParser constructor might throw
            throw new RuntimeException("Failed to instantiate EnumParser for " + specificEnumClass.getName(), e);
        }
    }

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
                        if (parserClass == EnumParser.class) { // Assuming EnumParser is your class for this
                            if (!fieldType.isEnum()) {
                                throw new IllegalArgumentException("Field '" + field.getName() + "' in class '" + this.clazz.getSimpleName() +
                                        "' is annotated with EnumParser but is not an Enum type.");
                            }
                            parserInstance = createEnumParserInstance(fieldType);

                        } else {
                            parserInstance = parserClass.getDeclaredConstructor().newInstance();
                        }
                        elementParsers.put(fieldKey, parserInstance);
                    } catch (NoSuchMethodException e) { // For non-EnumParser default constructors
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


    @Override
    public boolean canMap(DataRecord record) {
        return record != null && record.fields().size() == annotatedFields.size() &&
                record.fields().stream().allMatch(field -> annotatedFields.containsKey(field.name().toLowerCase()));
    }

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
            // Catch any other exceptions that might occur
            throw new RuntimeException("Failed to set field value: " + e.getMessage(), e);
        }
    }
}
