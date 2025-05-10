package fr.dl11.openmedia.datasource.mappers;

import fr.dl11.openmedia.datasource.data.DataRecord;

public interface IDataMapper<T> {
    boolean canMap(DataRecord record);

    T mapData(DataRecord record) throws MatchException;
}
