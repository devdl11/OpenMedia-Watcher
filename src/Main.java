import fr.dl11.openmedia.MediaWatcher;
import fr.dl11.openmedia.common.events.NewRawDataEvent;
import fr.dl11.openmedia.core.EventBus;
import fr.dl11.openmedia.datasource.data.SourcedRawData;
import fr.dl11.openmedia.types.DataType;
import fr.dl11.openmedia.utils.URLDownloader;

import java.io.IOException;

public class Main {
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

    public static void main(String[] args) {
        MediaWatcher mediaWatcher = new MediaWatcher();
        init();
        mediaWatcher.run();
    }
}