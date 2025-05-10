package fr.dl11.openmedia.datasource.parsers;

import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.exceptions.ParsingException;
import fr.dl11.openmedia.types.DataType;

import java.util.Collection;

public interface IDataParser {
    boolean canParse(RawData rawData);

    Collection<DataRecord> parseData(RawData data) throws ParsingException;
}
