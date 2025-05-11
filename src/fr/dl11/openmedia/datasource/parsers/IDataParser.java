package fr.dl11.openmedia.datasource.parsers;

import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.exceptions.ParsingException;

import java.util.Collection;

/**
 * Interface for parsing raw data into structured data records.
 *
 * <p>The {@code IDataParser} interface defines methods for determining if a given
 * raw data object can be parsed and for performing the parsing operation. Implementations
 * of this interface are responsible for handling specific data formats and ensuring
 * that the parsed data is returned as a collection of {@link DataRecord}.
 */
public interface IDataParser {

    /**
     * Checks if the given raw data can be parsed by this parser.
     *
     * <p>The method evaluates the raw data object to determine if it matches the
     * expected format or type that this parser can handle.
     *
     * @param rawData The raw data to check.
     * @return {@code true} if the raw data can be parsed, {@code false} otherwise.
     */
    boolean canParse(RawData rawData);

    /**
     * Parses the given raw data into a collection of {@link DataRecord}.
     *
     * <p>The method processes the raw data and converts it into structured data
     * records. If the raw data is invalid or cannot be parsed, a {@link ParsingException}
     * is thrown.
     *
     * @param data The raw data to parse.
     * @return A collection of {@link DataRecord} representing the parsed data.
     * @throws ParsingException If the raw data is invalid or cannot be parsed.
     */
    Collection<DataRecord> parseData(RawData data) throws ParsingException;
}
