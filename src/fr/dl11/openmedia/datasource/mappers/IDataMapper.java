package fr.dl11.openmedia.datasource.mappers;

import fr.dl11.openmedia.datasource.data.DataRecord;

/**
 * Interface for mapping data records to objects of a specified type.
 *
 * <p>The {@code IDataMapper} interface defines methods for checking if a data record
 * can be mapped and for performing the mapping operation. Implementations of this
 * interface are responsible for converting {@link DataRecord} instances into objects
 * of type {@code T}.
 *
 * @param <T> The type of object to map the data to.
 */
public interface IDataMapper<T> {

    /**
     * Checks if the given {@link DataRecord} can be mapped to an object of type {@code T}.
     *
     * @param record The data record to check.
     * @return {@code true} if the record can be mapped, {@code false} otherwise.
     */
    boolean canMap(DataRecord record);

    /**
     * Maps the given {@link DataRecord} to an object of type {@code T}.
     *
     * @param record The data record to map.
     * @return An object of type {@code T} mapped from the data record.
     * @throws MatchException If the mapping fails.
     */
    T mapData(DataRecord record) throws MatchException;
}