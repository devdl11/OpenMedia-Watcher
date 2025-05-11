package fr.dl11.openmedia.datasource.parsers.elements;

import fr.dl11.openmedia.datasource.exceptions.ElementParsingException;

/**
 * A parser implementation for converting string data into integer values.
 *
 * <p>The {@code IntegerParser} class implements the {@link IElementParser} interface
 * and provides functionality to parse string representations of integers.
 * If the input string is not a valid integer, an {@link ElementParsingException}
 * is thrown.
 */
public class IntegerParser implements IElementParser<Integer> {

    /**
     * Parses the given string data into an integer value.
     *
     * <p>The method uses {@link Integer#parseInt(String)} to convert the input string
     * into an integer. If the input string is not a valid integer, a
     * {@link NumberFormatException} is caught and an {@link ElementParsingException}
     * is thrown with an appropriate error message.
     *
     * @param data The string data to parse.
     * @return The parsed integer value.
     * @throws ElementParsingException If the input string is not a valid integer.
     */
    @Override
    public Integer parse(String data) throws ElementParsingException {
        try {
            return Integer.parseInt(data);
        } catch (NumberFormatException e) {
            throw new ElementParsingException("Invalid integer format: " + data);
        }
    }
}