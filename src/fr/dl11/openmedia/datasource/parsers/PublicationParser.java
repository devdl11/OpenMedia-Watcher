package fr.dl11.openmedia.datasource.parsers;

import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.data.FieldData;
import fr.dl11.openmedia.datasource.data.RawData;
import fr.dl11.openmedia.datasource.exceptions.ParsingException;
import fr.dl11.openmedia.types.DataType;

import java.util.Collection;
import java.util.List;

public class PublicationParser implements IDataParser {

    @Override
    public boolean canParse(RawData rawData) {
        return rawData.dataType() == DataType.ARTICLE && rawData.data() != null
                && rawData.data().split("\n\n").length == 5;
    }

    @Override
    public Collection<DataRecord> parseData(RawData data) throws ParsingException {
        assert data != null && canParse(data);

        String[] lines = data.data().split("\n\n");

        return List.of(
                new DataRecord(
                        List.of(
                                new FieldData("type", lines[0]),
                                new FieldData("mediaName", lines[1]),
                                new FieldData("title", lines[2]),
                                new FieldData("author", lines[3]),
                                new FieldData("content", lines[4])
                        )
                )
        );
    }
}
