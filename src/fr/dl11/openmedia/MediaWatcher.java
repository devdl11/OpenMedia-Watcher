package fr.dl11.openmedia;

import fr.dl11.openmedia.common.events.NewRawDataEvent;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.datasource.DataPipelineManager;
import fr.dl11.openmedia.datasource.mappers.DefaultDataMapper;
import fr.dl11.openmedia.datasource.parsers.CSVParser;
import fr.dl11.openmedia.events.EventHandler;
import fr.dl11.openmedia.exceptions.FailedEventHandling;
import fr.dl11.openmedia.types.DataType;

import java.util.List;

public class MediaWatcher implements EventHandler<NewRawDataEvent> {
    private final DataPipelineManager dataPipelineManager;

    private void initializePipeline() {
        dataPipelineManager.addParser(DataType.CSV, new CSVParser());
        dataPipelineManager.addParser(DataType.CSV, new CSVParser("\t"));
        // Add other parsers and mappers as needed

        dataPipelineManager.addMapper(new DefaultDataMapper<>(MediaData.class));
    }

    public MediaWatcher(DataPipelineManager dataPipelineManager) {
        this.dataPipelineManager = dataPipelineManager;

        initializePipeline();
    }

    @Override
    public void handleEvent(NewRawDataEvent event) throws FailedEventHandling {
        assert event != null;
        dataPipelineManager.handleData(event.getRawData());
    }
}
