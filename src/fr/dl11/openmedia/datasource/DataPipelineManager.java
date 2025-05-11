package fr.dl11.openmedia.datasource;

import fr.dl11.openmedia.common.events.NewRawDataEvent;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.data.SourcedRawData;
import fr.dl11.openmedia.datasource.events.NewIncomingDataEvent;
import fr.dl11.openmedia.datasource.exceptions.ParsingException;
import fr.dl11.openmedia.datasource.mappers.IDataMapper;
import fr.dl11.openmedia.datasource.mappers.SourcedDataMapper;
import fr.dl11.openmedia.datasource.parsers.IDataParser;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.exceptions.FailedEventHandling;
import fr.dl11.openmedia.types.DataType;

import java.util.*;

/**
 * Manages the data processing pipeline by coordinating parsers and mappers.
 *
 * <p>The {@code DataPipelineManager} class listens for {@link NewRawDataEvent} events,
 * processes the raw data using registered parsers, and maps the parsed data using
 * registered mappers. The resulting data is published as {@link NewIncomingDataEvent}.
 */
public class DataPipelineManager implements EventHandler<NewRawDataEvent> {

    /**
     * A map of data types to their corresponding parsers.
     */
    private final Map<DataType, List<IDataParser>> parsers;

    /**
     * A list of registered data mappers.
     */
    private final List<IDataMapper<?>> mappers;

    /**
     * Constructs a new {@code DataPipelineManager} and subscribes to the {@link NewRawDataEvent}.
     */
    public DataPipelineManager() {
        this.parsers = new HashMap<>();
        this.mappers = new ArrayList<>();

        EventBus.getInstance().subscribe(NewRawDataEvent.class, this);
    }

    /**
     * Adds a parser for a specific data type.
     *
     * @param dataType The data type the parser can handle.
     * @param parser   The parser to add.
     */
    public void addParser(DataType dataType, IDataParser parser) {
        parsers.computeIfAbsent(dataType, k -> new ArrayList<>())
                .add(parser);
    }

    /**
     * Removes a parser for a specific data type.
     *
     * @param dataType The data type the parser is associated with.
     * @param parser   The parser to remove.
     */
    public void removeParser(DataType dataType, IDataParser parser) {
        parsers.computeIfPresent(dataType, (k, v) -> {
            v.remove(parser);
            return v.isEmpty() ? null : v;
        });
    }

    /**
     * Adds a data mapper to the pipeline.
     *
     * @param mapper The mapper to add.
     */
    public void addMapper(IDataMapper<?> mapper) {
        mappers.add(mapper);
    }

    /**
     * Removes a data mapper from the pipeline.
     *
     * @param mapper The mapper to remove.
     */
    public void removeMapper(IDataMapper<?> mapper) {
        mappers.remove(mapper);
    }

    /**
     * Handles a {@link NewRawDataEvent} by parsing and mapping the raw data.
     *
     * <p>The method identifies the appropriate parser for the raw data, processes it
     * into {@link DataRecord} objects, and then maps the records using the registered
     * mappers. The resulting data is published as {@link NewIncomingDataEvent}.
     *
     * @param event The event containing the raw data to process.
     * @throws FailedEventHandling If parsing or mapping fails.
     */
    @Override
    public void handleEvent(NewRawDataEvent event) throws FailedEventHandling {
        assert event != null && event.getRawData() != null;

        event.consume();

        Collection<DataRecord> parsedData = null;
        RawData rawData = event.getRawData();

        for (List<IDataParser> parser : parsers.values()) {
            for (IDataParser p : parser) {
                if (!p.canParse(event.getRawData()))
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

        String source = null;
        if (rawData instanceof SourcedRawData sourcedRawData) {
            source = sourcedRawData.source();
        }

        for (DataRecord record : parsedData) {
            for (IDataMapper<?> mapper : mappers) {
                if (source != null && mapper instanceof SourcedDataMapper<?> sourcedDataMapper) {
                    if (!sourcedDataMapper.canMap(record, source))
                        continue;
                } else if (!mapper.canMap(record))
                    continue;

                Object output = mapper.mapData(record);
                if (output == null) {
                    throw new IllegalArgumentException("Mapper returned null for record: " + record);
                }

                EventBus.getInstance().publish(new NewIncomingDataEvent(output));
                break;

            }
        }
    }
}