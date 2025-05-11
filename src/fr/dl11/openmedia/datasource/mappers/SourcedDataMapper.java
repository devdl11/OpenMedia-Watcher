package fr.dl11.openmedia.datasource.mappers;

import fr.dl11.openmedia.datasource.data.DataRecord;

import java.util.Collection;

public class SourcedDataMapper<T> implements IDataMapper<T> {
    private final IDataMapper<T> mapper;
    private final Collection<String> sources;

    public SourcedDataMapper(IDataMapper<T> mapper, Collection<String> sources) {
        this.mapper = mapper;
        this.sources = sources;
    }

    @Override
    public boolean canMap(DataRecord record) {
        return mapper.canMap(record);
    }

    public boolean canMap(DataRecord record, String source) {
        return sources.contains(source) && mapper.canMap(record);
    }

    @Override
    public T mapData(DataRecord record) throws MatchException {
       return mapper.mapData(record);
    }
}
