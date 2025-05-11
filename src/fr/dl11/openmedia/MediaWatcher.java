package fr.dl11.openmedia;

import fr.dl11.openmedia.common.events.NewRawDataEvent;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.data.mapper.PersonMapper;
import fr.dl11.openmedia.data.organization.OrganizationData;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.data.organization.OrganizationOrganizationLink;
import fr.dl11.openmedia.data.persons.PersonMediaLinkData;
import fr.dl11.openmedia.data.persons.PersonOrganizationLink;
import fr.dl11.openmedia.datasource.DataPipelineManager;
import fr.dl11.openmedia.datasource.mappers.DataMapperWithIgnores;
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

        // Add mappers for media data
        dataPipelineManager.addMapper(new DefaultDataMapper<>(MediaData.class));

        // Add mappers for persons
        dataPipelineManager.addMapper(new PersonMapper());
        dataPipelineManager.addMapper(new DefaultDataMapper<>(PersonMediaLinkData.class));
        dataPipelineManager.addMapper(new DataMapperWithIgnores<>(PersonOrganizationLink.class, List.of("id")));

        // Add mappers for organizations
        dataPipelineManager.addMapper(new DefaultDataMapper<>(OrganizationData.class));
        dataPipelineManager.addMapper(new DataMapperWithIgnores<>(OrganizationMediaLink.class, List.of("id")));
        dataPipelineManager.addMapper(new DataMapperWithIgnores<>(OrganizationOrganizationLink.class, List.of("id")));
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
