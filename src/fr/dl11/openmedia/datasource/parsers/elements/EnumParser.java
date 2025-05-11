package fr.dl11.openmedia.datasource.parsers.elements;

/**
 * A parser implementation for converting string data into enum values.
 *
 * <p>The {@code EnumParser} class implements the {@link IElementParser} interface
 * and provides functionality to parse string representations of enum constants
 * into their corresponding enum values. If the input string does not match any
 * enum constant, a default value (if provided) is returned.
 *
 * @param <E> The type of the enum being parsed.
 */
public class EnumParser<E extends Enum<E>> implements IElementParser<E> {

    /**
     * The class of the enum being parsed.
     */
    private final Class<E> enumClass;

    /**
     * The default value to return if the input string does not match any enum constant.
     */
    private final E defaultValue;

    /**
     * Constructs an {@code EnumParser} with the specified enum class and default value.
     *
     * @param enumClass    The class of the enum being parsed.
     * @param defaultValue The default value to return if parsing fails.
     */
    public EnumParser(Class<E> enumClass, E defaultValue) {
        this.enumClass = enumClass;
        this.defaultValue = defaultValue;
    }

    /**
     * Constructs an {@code EnumParser} with the specified enum class and no default value.
     *
     * @param enumClass The class of the enum being parsed.
     */
    public EnumParser(Class<E> enumClass) {
        this(enumClass, null);
    }

    /**
     * Parses the given string into an enum value.
     *
     * <p>The method iterates through the constants of the enum class and compares
     * their string representations with the input value. If a match is found, the
     * corresponding enum constant is returned. If no match is found, the default
     * value (if provided) is returned.
     *
     * @param value The string representation of the enum constant to parse.
     * @return The parsed enum value, or the default value if parsing fails.
     */
    public E parse(String value) {
        for (E constant : enumClass.getEnumConstants())
            if (constant.toString().equals(value)) return constant;

        return defaultValue;
    }
}
