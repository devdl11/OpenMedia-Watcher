import fr.dl11.openmedia.MediaWatcher;
import fr.dl11.openmedia.common.events.NewRawDataEvent;
import fr.dl11.openmedia.datasource.DataPipelineManager;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.types.DataType;
import fr.dl11.openmedia.utils.URLDownloader;

import java.io.IOException;

public class Main {
    private void init() {

    }

    public static void main(String[] args) {
        MediaWatcher mediaWatcher = new MediaWatcher(new DataPipelineManager());

        String remoteMediaTSV = "https://raw.githubusercontent.com/mdiplo/Medias_francais/refs/heads/master/medias.tsv";
        // Get the data from the remote media TSV
        try {
            String content = URLDownloader.downloadContent(remoteMediaTSV);
            mediaWatcher.handleEvent(new NewRawDataEvent(new RawData(content, DataType.CSV)));
        } catch (Exception e) {
            System.err.println("Failed to download the file: " + e.getMessage());
            return;
        }


    }
}