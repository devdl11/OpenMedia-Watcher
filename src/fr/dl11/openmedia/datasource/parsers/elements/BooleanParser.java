package fr.dl11.openmedia.datasource.parsers.elements;

/**
 * A parser implementation for converting string data into boolean values.
 *
 * <p>The {@code BooleanParser} class implements the {@link IElementParser} interface
 * and provides functionality to parse string representations of boolean values.
 * It uses the {@link Boolean#parseBoolean(String)} method to perform the conversion.
 */
public class BooleanParser implements IElementParser<Boolean> {

    /**
     * Parses the given string data into a boolean value.
     *
     * <p>The method uses {@link Boolean#parseBoolean(String)} to convert the input string
     * into a boolean. The conversion is case-insensitive and treats "true" (ignoring case)
     * as {@code true}, and any other value as {@code false}.
     *
     * @param data The string data to parse.
     * @return The parsed boolean value.
     */
    @Override
    public Boolean parse(String data) {
        return Boolean.parseBoolean(data);
    }
}
