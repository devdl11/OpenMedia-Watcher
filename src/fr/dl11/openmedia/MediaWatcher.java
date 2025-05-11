package fr.dl11.openmedia;

import fr.dl11.openmedia.console.Console;
import fr.dl11.openmedia.contextualizers.*;
import fr.dl11.openmedia.core.DataGraphManager;
import fr.dl11.openmedia.data.MediaData;
import fr.dl11.openmedia.data.PublicationData;
import fr.dl11.openmedia.data.mapper.PersonMapper;
import fr.dl11.openmedia.data.organization.OrganizationData;
import fr.dl11.openmedia.data.organization.OrganizationMediaLink;
import fr.dl11.openmedia.data.organization.OrganizationOrganizationLink;
import fr.dl11.openmedia.data.person.PersonMediaLinkData;
import fr.dl11.openmedia.data.person.PersonOrganizationLink;
import fr.dl11.openmedia.datasource.DataPipelineManager;
import fr.dl11.openmedia.datasource.mappers.DataMapperWithIgnores;
import fr.dl11.openmedia.datasource.mappers.DefaultDataMapper;
import fr.dl11.openmedia.datasource.mappers.SourcedDataMapper;
import fr.dl11.openmedia.datasource.parsers.CSVParser;
import fr.dl11.openmedia.datasource.parsers.PublicationParser;
import fr.dl11.openmedia.types.DataType;

import java.util.List;

public class MediaWatcher {
    private final DataPipelineManager dataPipelineManager;
    private final DataGraphManager dataGraphManager;

    private final GraphDataContextualizers personDataContextualizer;
    private final GraphDataContextualizers mediaDataContextualizer;
    private final GraphDataContextualizers organizationDataContextualizer;
    private final GraphDataContextualizers publicationDataContextualizer;

    private final Console console;

    private void initializePipeline() {
        dataPipelineManager.addParser(DataType.CSV, new CSVParser());
        dataPipelineManager.addParser(DataType.CSV, new CSVParser("\t"));
        dataPipelineManager.addParser(DataType.ARTICLE, new PublicationParser());
        // Add other parsers and mappers as needed

        // Add mappers for media data
        dataPipelineManager.addMapper(new DefaultDataMapper<>(MediaData.class));

        // Add mappers for persons
        dataPipelineManager.addMapper(new PersonMapper());
        dataPipelineManager.addMapper(new DefaultDataMapper<>(PersonMediaLinkData.class));
        dataPipelineManager.addMapper(
                new SourcedDataMapper<>(
                        new DataMapperWithIgnores<>(PersonOrganizationLink.class, List.of("id")),
                        List.of("personne-organisation")
                ));

        // Add mappers for organizations
        dataPipelineManager.addMapper(new DefaultDataMapper<>(OrganizationData.class));
        dataPipelineManager.addMapper(
                new SourcedDataMapper<>(
                        new DataMapperWithIgnores<>(OrganizationMediaLink.class, List.of("id")),
                        List.of("organisation-media")
                ));
        dataPipelineManager.addMapper(
                new SourcedDataMapper<>(
                        new DataMapperWithIgnores<>(OrganizationOrganizationLink.class, List.of("id")),
                        List.of("organisation-organisation")
                ));

        dataPipelineManager.addMapper(
                new SourcedDataMapper<>(
                        new DefaultDataMapper<>(PublicationData.class),
                        List.of("publication")
                ));
    }

    public MediaWatcher() {
        dataGraphManager = new DataGraphManager();

        dataPipelineManager = new DataPipelineManager();
        initializePipeline();

        personDataContextualizer       = new PersonDataContextualizer(dataGraphManager);
        mediaDataContextualizer        = new MediaDataContextualizer(dataGraphManager);
        organizationDataContextualizer = new OrganizationDataContextualizer(dataGraphManager);
        publicationDataContextualizer  = new PublicationDataContextualizers(dataGraphManager);

        console = new Console(dataGraphManager);
    }


    public void run() {
        console.show();
    }
}
