package fr.dl11.openmedia.datasource.mappers;

import fr.dl11.openmedia.datasource.data.DataRecord;

import java.util.ArrayList;
import java.util.Collection;

public class DataMapperWithIgnores<T> extends DefaultDataMapper<T> {
    private final Collection<String> ignoredFields;

    public DataMapperWithIgnores(Class<T> clazz) {
        super(clazz);
        this.ignoredFields = new ArrayList<>();
    }

    public DataMapperWithIgnores(Class<T> clazz, Collection<String> ignoredFields) {
        super(clazz);
        this.ignoredFields = ignoredFields;
    }

    @Override
    public boolean canMap(DataRecord record) {
        return super.canMap(
                new DataRecord(
                        record
                                .fields()
                                .stream()
                                .filter(fieldData ->
                !ignoredFields.contains(fieldData.name().toLowerCase())
                                ).toList()
                ));
    }

    @Override
    public T mapData(DataRecord record) throws MatchException {
        return super.mapData(
                new DataRecord(
                        record
                                .fields()
                                .stream()
                                .filter(fieldData ->
                !ignoredFields.contains(fieldData.name().toLowerCase())
                                ).toList()
                ));
    }
}
