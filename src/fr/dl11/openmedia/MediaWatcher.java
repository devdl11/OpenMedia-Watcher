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

/**
 * Main class for managing media-related data and operations.
 *
 * <p>The {@code MediaWatcher} class initializes and manages the data pipeline,
 * data graph, and contextualizers for various types of data. It also provides
 * a console interface for interacting with the data graph.</p>
 */
public class MediaWatcher {
    private final DataPipelineManager dataPipelineManager;
    private final DataGraphManager dataGraphManager;

    private final GraphDataContextualizers personDataContextualizer;
    private final GraphDataContextualizers mediaDataContextualizer;
    private final GraphDataContextualizers organizationDataContextualizer;
    private final GraphDataContextualizers publicationDataContextualizer;

    private final Console console;

    /**
     * Initializes the data pipeline with parsers and mappers for various data types.
     *
     * <p>This method configures the {@link DataPipelineManager} by adding parsers
     * for CSV and article data, as well as mappers for media, person, organization,
     * and publication data. It also sets up specialized mappers for linking data.</p>
     */
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

    /**
     * Constructs a new {@code MediaWatcher} instance.
     *
     * <p>This constructor initializes the data graph manager, data pipeline manager,
     * and contextualizers for person, media, organization, and publication data.
     * It also sets up the console interface for user interaction.</p>
     */
    public MediaWatcher() {
        dataGraphManager = new DataGraphManager();

        dataPipelineManager = new DataPipelineManager();
        initializePipeline();

        personDataContextualizer = new PersonDataContextualizer(dataGraphManager);
        mediaDataContextualizer = new MediaDataContextualizer(dataGraphManager);
        organizationDataContextualizer = new OrganizationDataContextualizer(dataGraphManager);
        publicationDataContextualizer = new PublicationDataContextualizers(dataGraphManager);

        console = new Console(dataGraphManager);
    }

    /**
     * Starts the media watcher application.
     *
     * <p>This method displays the console interface, allowing the user to interact
     * with the data graph and perform various operations.</p>
     */
    public void run() {
        console.show();
    }
}