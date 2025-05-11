import fr.dl11.openmedia.MediaWatcher;
import fr.dl11.openmedia.common.events.NewRawDataEvent;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.datasource.data.SourcedRawData;
import fr.dl11.openmedia.types.DataType;
import fr.dl11.openmedia.utils.URLDownloader;

import java.io.IOException;

/**
 * Main class for initializing and running the MediaWatcher application.
 *
 * <p>The {@code Main} class is the entry point of the application. It initializes
 * the data by downloading remote TSV files, publishing them as events, and then
 * starts the {@link MediaWatcher} application.</p>
 */
public class Main {

    /**
     * Initializes the application by downloading remote data files and publishing events.
     *
     * <p>This method downloads a set of predefined TSV files from remote URLs, processes
     * their content, and publishes {@link NewRawDataEvent} instances to the {@link EventBus}.
     * Each file is associated with a specific {@link DataType} and a file name derived
     * from the URL.</p>
     *
     * <p>If a file fails to download, an error message is printed to the console. Any
     * other exceptions are rethrown as runtime exceptions.</p>
     */
    private static void init() {
        String remoteMediaTSV = "https://raw.githubusercontent.com/mdiplo/Medias_francais/refs/heads/master/medias.tsv";
        String remotePersonsTSV = "https://raw.githubusercontent.com/mdiplo/Medias_francais/refs/heads/master/personnes.tsv";
        String remoteOrganisationsTSV = "https://raw.githubusercontent.com/mdiplo/Medias_francais/refs/heads/master/organisations.tsv";

        // links
        String remotePersonMediaTSV = "https://raw.githubusercontent.com/mdiplo/Medias_francais/refs/heads/master/personne-media.tsv";
        String remotePersonOrganisationTSV = "https://raw.githubusercontent.com/mdiplo/Medias_francais/refs/heads/master/personne-organisation.tsv";
        String remoteOrganisationMediaTSV = "https://raw.githubusercontent.com/mdiplo/Medias_francais/refs/heads/master/organisation-media.tsv";
        String remoteOrganisationOrganisationTSV = "https://raw.githubusercontent.com/mdiplo/Medias_francais/refs/heads/master/organisation-organisation.tsv";

        String[] links = {
                remoteMediaTSV,
                remotePersonsTSV,
                remoteOrganisationsTSV,
                remotePersonMediaTSV,
                remotePersonOrganisationTSV,
                remoteOrganisationMediaTSV,
                remoteOrganisationOrganisationTSV
        };

        for (String link : links) {
            try {
                String content = URLDownloader.downloadContent(link);
                String fileName = link
                        .substring(link.lastIndexOf("/") + 1)
                        .replace(".tsv", "");

                EventBus.getInstance().publish(
                        new NewRawDataEvent(
                                new SourcedRawData(content, DataType.CSV, fileName)
                        ));

            } catch (IOException e) {
                System.err.println("Failed to download the file: " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Main method to start the application.
     *
     * <p>This method creates an instance of {@link MediaWatcher}, initializes the
     * application by calling {@link #init()}, and then starts the media watcher
     * by invoking its {@link MediaWatcher#run()} method.</p>
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        MediaWatcher mediaWatcher = new MediaWatcher();
        init();
        mediaWatcher.run();
    }
}