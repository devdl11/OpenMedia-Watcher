package fr.dl11.openmedia.datasource.exceptions;

/**
 * Represents an exception that occurs during data parsing.
 *
 * <p>The {@code ParsingException} class extends {@link Exception} and provides
 * constructors to create exceptions with detailed error messages and optional
 * causes. It is used to indicate issues encountered while parsing data.
 */
public class ParsingException extends Exception {

    /**
     * Constructs a new {@code ParsingException} with the specified data.
     *
     * @param data The data that could not be parsed.
     */
    public ParsingException(String data) {
        super("Could not parse the data. \n" +
                "Data: " + data);
    }

    /**
     * Constructs a new {@code ParsingException} with the specified details and data.
     *
     * @param details Additional details about the parsing error.
     * @param data    The data that could not be parsed.
     */
    public ParsingException(String details, String data) {
        super("Could not parse the data: " + details + "\n" +
                "Data: " + data);
    }

    /**
     * Constructs a new {@code ParsingException} with the specified details, data, and cause.
     *
     * @param details Additional details about the parsing error.
     * @param data    The data that could not be parsed.
     * @param cause   The underlying cause of the parsing error.
     */
    public ParsingException(String details, String data, Throwable cause) {
        super("Could not parse the data: " + details + "\n" +
                "Data: " + data, cause);
    }
}