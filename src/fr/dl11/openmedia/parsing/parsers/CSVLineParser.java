package fr.dl11.openmedia.parsing.parsers;

import fr.dl11.openmedia.data.BaseData;
import fr.dl11.openmedia.parsing.ITokenParser;

import java.util.List;

public abstract class CSVLineParser<T extends BaseData> {
    private final List<? extends ITokenParser<?>> parsers;
    private final char delimiter;

    public CSVLineParser(List<? extends ITokenParser<?>> parsers, char delimiter) {
        this.parsers = parsers;
        this.delimiter = delimiter;
    }

    public CSVLineParser(List<? extends ITokenParser<?>> parsers) {
        this(parsers, ',');
    }

    protected abstract T createData(Object[] values);

    public T parseLine(String line) throws Exception {
        String[] values = line.split(String.valueOf(delimiter));

        if (values.length != parsers.size()) {
            throw new IllegalArgumentException("Line does not match the number of parsers");
        }

        Object[] parsedValues = new Object[parsers.size()];
        for (int i = 0; i < parsers.size(); i++) {
            var parser = parsers.get(i);
            parsedValues[i] = parser.parse(values[i]);
        }

        return createData(parsedValues);
    }
}
