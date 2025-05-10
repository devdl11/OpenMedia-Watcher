package fr.dl11.openmedia.datasource;

import fr.dl11.openmedia.datasource.data.RawData;

public interface IRawDataHandler {
    void handleData(RawData rawData) throws Exception;
}
