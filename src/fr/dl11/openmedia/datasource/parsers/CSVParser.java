package fr.dl11.openmedia.datasource.parsers;

import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.FieldData;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.exceptions.ParsingException;
import fr.dl11.openmedia.types.DataType;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

public class CSVParser implements IDataParser {
    private final String delimiter;

    public CSVParser() {
        this(",");
    }

    public CSVParser(String delimiter) {
        assert delimiter != null && !delimiter.isEmpty();
        this.delimiter = delimiter;
    }

    @Override
    public boolean canParse(RawData rawData) {
        return rawData.dataType() == DataType.CSV && rawData.data() != null
                && rawData.data().lines().findFirst().isPresent()
                && rawData.data().lines().findFirst().get().contains(delimiter);
    }

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
