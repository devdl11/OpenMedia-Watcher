package fr.dl11.openmedia.datasource;

import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.exceptions.ParsingException;
import fr.dl11.openmedia.datasource.mappers.IDataMapper;
import fr.dl11.openmedia.datasource.parsers.IDataParser;
import fr.dl11.openmedia.exceptions.FailedEventHandling;
import fr.dl11.openmedia.types.DataType;

import java.util.*;

public class DataPipelineManager implements IRawDataHandler {
    private final Map<DataType, List<IDataParser>> parsers;
    private final List<IDataMapper<?>> mappers;

    public DataPipelineManager() {
        this.parsers = new HashMap<>();
        this.mappers = new ArrayList<>();
    }

    public void addParser(DataType dataType, IDataParser parser) {
        parsers.computeIfAbsent(dataType, k -> new ArrayList<>())
                .add(parser);
    }

    public void removeParser(DataType dataType, IDataParser parser) {
        parsers.computeIfPresent(dataType, (k, v) -> {
            v.remove(parser);
            return v.isEmpty() ? null : v;
        });
    }

    public void addMapper(IDataMapper<?> mapper) {
        mappers.add(mapper);
    }

    public void removeMapper(IDataMapper<?> mapper) {
        mappers.remove(mapper);
    }

    @Override
    public void handleData(RawData rawData) throws FailedEventHandling {
        Collection<DataRecord> parsedData = null;

        for (List<IDataParser> parser : parsers.values()) {
            for (IDataParser p : parser) {
                if (!p.canParse(rawData))
                    continue;

                try {
                    parsedData = p.parseData(rawData);
                } catch (ParsingException e) {
                    throw new FailedEventHandling("Failed to parse data: " + rawData);
                }

                break;
            }
        }

        if (parsedData == null) {
            throw new IllegalArgumentException("No parser found for data type: " + rawData.dataType());
        }

        for (DataRecord record : parsedData) {
            for (IDataMapper<?> mapper : mappers) {
                if (!mapper.canMap(record))
                    continue;

                Object output = mapper.mapData(record);
                if (output == null) {
                    throw new IllegalArgumentException("Mapper returned null for record: " + record);
                }

                System.out.println(output);

            }
        }
    }
}
