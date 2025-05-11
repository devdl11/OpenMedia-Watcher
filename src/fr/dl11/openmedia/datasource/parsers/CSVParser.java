package fr.dl11.openmedia.datasource.parsers;

import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.FieldData;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.exceptions.ParsingException;
import fr.dl11.openmedia.types.DataType;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * A parser implementation for processing CSV data.
 *
 * <p>The {@code CSVParser} class implements the {@link IDataParser} interface
 * and provides functionality to parse raw CSV data into a collection of {@link DataRecord}.
 * It supports configurable delimiters and validates the input data format before parsing.
 */
public class CSVParser implements IDataParser {
    /**
     * The delimiter used to separate fields in the CSV data.
     */
    private final String delimiter;

    /**
     * Constructs a {@code CSVParser} with the default delimiter (",").
     */
    public CSVParser() {
        this(",");
    }

    /**
     * Constructs a {@code CSVParser} with the specified delimiter.
     *
     * @param delimiter The delimiter to use for parsing CSV data. Must not be null or empty.
     * @throws AssertionError If the delimiter is null or empty.
     */
    public CSVParser(String delimiter) {
        assert delimiter != null && !delimiter.isEmpty();
        this.delimiter = delimiter;
    }

    /**
     * Checks if the given raw data can be parsed as CSV.
     *
     * <p>The method verifies that the data type is {@link DataType#CSV}, the data is not null,
     * and the first line of the data contains the specified delimiter.
     *
     * @param rawData The raw data to check.
     * @return {@code true} if the data can be parsed as CSV, {@code false} otherwise.
     */
    @Override
    public boolean canParse(RawData rawData) {
        return rawData.dataType() == DataType.CSV && rawData.data() != null
                && rawData.data().lines().findFirst().isPresent()
                && rawData.data().lines().findFirst().get().contains(delimiter);
    }

    /**
     * Parses the given raw CSV data into a collection of {@link DataRecord}.
     *
     * <p>The method processes the header row to extract field names and then parses
     * each subsequent line into {@link FieldData} objects. If a line has fewer values
     * than the header, it is padded with empty strings. If a line has more values,
     * the extra values are ignored.
     *
     * @param data The raw CSV data to parse.
     * @return A collection of {@link DataRecord} representing the parsed data.
     * @throws ParsingException If the data is null, cannot be parsed, or lacks a header row.
     */
    @Override
    public Collection<DataRecord> parseData(RawData data) throws ParsingException {
        assert data != null && canParse(data) && data.data() != null;

        Stream<String> lines = data.data().lines();

        // header
        String header = lines.findFirst().orElseThrow(() -> new IllegalArgumentException("No header found"));
        String[] headers = header.split(delimiter);

        lines = data.data().lines().skip(1);
        // data
        return lines.skip(1)
                .map(line -> line.split(delimiter))
                .map(values -> {
                    if (values.length != headers.length)
                        values = Arrays.copyOf(values, headers.length);

                    for (int i = 0; i < values.length; i++)
                        //noinspection ConstantConditions
                        if (values[i] == null)
                            values[i] = "";

                    FieldData[] fields = new FieldData[headers.length];
                    for (int i = 0; i < headers.length; i++) {
                        fields[i] = new FieldData(headers[i], values[i]);
                    }

                    return new DataRecord(Arrays.asList(fields));
                })
                .toList();
    }
}