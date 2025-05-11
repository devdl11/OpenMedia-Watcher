package fr.dl11.openmedia.datasource.exceptions;

/**
 * Exception thrown when an element cannot be parsed.
 *
 * <p>The {@code ElementParsingException} class extends {@link ParsingException}
 * and provides a specific exception type for errors encountered during the
 * parsing of elements. It includes a message indicating the failure and the
 * value that caused the error.
 */
public class ElementParsingException extends ParsingException {

    /**
     * Constructs a new {@code ElementParsingException} with the specified value.
     *
     * @param value The value that could not be parsed.
     */
    public ElementParsingException(String value) {
        super("Could not parse the element. \n", value);
    }
}
