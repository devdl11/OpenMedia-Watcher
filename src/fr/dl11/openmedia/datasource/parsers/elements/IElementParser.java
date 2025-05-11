package fr.dl11.openmedia.datasource.parsers.elements;

import fr.dl11.openmedia.datasource.exceptions.ElementParsingException;

/**
 * Interface for parsing string values into objects of a specified type.
 *
 * <p>The {@code IElementParser} interface defines a method for converting
 * string representations into objects of type {@code T}. Implementations
 * of this interface are responsible for handling the parsing logic and
 * throwing an exception if the parsing fails.
 *
 * @param <T> The type of object to parse the string into.
 */
public interface IElementParser<T> {

    /**
     * Parses the given string value into an object of type {@code T}.
     *
     * @param value The string value to parse.
     * @return The parsed object of type {@code T}.
     * @throws ElementParsingException If the parsing fails.
     */
    T parse(String value) throws ElementParsingException;
}