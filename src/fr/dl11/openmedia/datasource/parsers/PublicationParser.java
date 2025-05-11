package fr.dl11.openmedia.datasource.parsers;

import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.FieldData;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.exceptions.ParsingException;
import fr.dl11.openmedia.types.DataType;

import java.util.Collection;
import java.util.List;

/**
 * A parser implementation for processing publication data.
 *
 * <p>The {@code PublicationParser} class implements the {@link IDataParser} interface
 * and provides functionality to parse raw publication data into a collection of
 * {@link DataRecord}. The parser expects the data to be of type {@link DataType#ARTICLE}
 * and formatted with five sections separated by double newlines.
 */
public class PublicationParser implements IDataParser {

    /**
     * Checks if the given raw data can be parsed as a publication.
     *
     * <p>The method verifies that the data type is {@link DataType#ARTICLE}, the data
     * is not null, and it contains exactly five sections separated by double newlines.
     *
     * @param rawData The raw data to check.
     * @return {@code true} if the data can be parsed as a publication, {@code false} otherwise.
     */
    @Override
    public boolean canParse(RawData rawData) {
        return rawData.dataType() == DataType.ARTICLE && rawData.data() != null
                && rawData.data().split("\n\n").length == 5;
    }

    /**
     * Parses the given raw publication data into a collection of {@link DataRecord}.
     *
     * <p>The method splits the raw data into five sections: type, media name, title,
     * author, and content. Each section is mapped to a {@link FieldData} object, and
     * the resulting {@link DataRecord} is returned as a collection.
     *
     * @param data The raw publication data to parse.
     * @return A collection of {@link DataRecord} representing the parsed publication data.
     * @throws ParsingException If the raw data is null or cannot be parsed.
     */
    @Override
    public Collection<DataRecord> parseData(RawData data) throws ParsingException {
        assert data != null && canParse(data);

        String[] lines = data.data().split("\n\n");

        return List.of(
                new DataRecord(
                        List.of(
                                new FieldData("type", lines[0]),
                                new FieldData("mediaName", lines[1]),
                                new FieldData("title", lines[2]),
                                new FieldData("author", lines[3]),
                                new FieldData("content", lines[4])
                        )
                )
        );
    }
}