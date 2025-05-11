package fr.dl11.openmedia.datasource.parsers.elements;

/**
 * A parser implementation for handling string data.
 *
 * <p>The {@code StringParser} class implements the {@link IElementParser} interface
 * and provides functionality to parse string data. Since the input is already
 * a string, this parser simply returns the input as-is.
 */
public class StringParser implements IElementParser<String> {

    /**
     * Parses the given string data and returns it as-is.
     *
     * <p>This method does not perform any transformation or validation on the input
     * string. It simply returns the input string unchanged.
     *
     * @param data The string data to parse.
     * @return The input string, unchanged.
     */
    @Override
    public String parse(String data) {
        return data;
    }
}
