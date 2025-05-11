package fr.dl11.openmedia.datasource.mappers;

import fr.dl11.openmedia.datasource.data.DataRecord;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A data mapper that allows ignoring specific fields during mapping.
 *
 * <p>The {@code DataMapperWithIgnores} class extends {@link DefaultDataMapper}
 * and provides functionality to exclude certain fields from being mapped.
 * This is useful when some fields in the data record are not relevant or
 * should be ignored during the mapping process.
 *
 * @param <T> The type of object to map the data to.
 */
public class DataMapperWithIgnores<T> extends DefaultDataMapper<T> {

    /**
     * A collection of field names to be ignored during mapping.
     */
    private final Collection<String> ignoredFields;

    /**
     * Constructs a new {@code DataMapperWithIgnores} with the specified class type
     * and an empty list of ignored fields.
     *
     * @param clazz The class type to map the data to.
     */
    public DataMapperWithIgnores(Class<T> clazz) {
        super(clazz);
        this.ignoredFields = new ArrayList<>();
    }

    /**
     * Constructs a new {@code DataMapperWithIgnores} with the specified class type
     * and a collection of field names to ignore.
     *
     * @param clazz         The class type to map the data to.
     * @param ignoredFields A collection of field names to be ignored during mapping.
     */
    public DataMapperWithIgnores(Class<T> clazz, Collection<String> ignoredFields) {
        super(clazz);
        this.ignoredFields = ignoredFields;
    }

    /**
     * Determines if the given {@link DataRecord} can be mapped, excluding the ignored fields.
     *
     * @param record The data record to check.
     * @return {@code true} if the record can be mapped, {@code false} otherwise.
     */
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

    /**
     * Maps the given {@link DataRecord} to an object of type {@code T}, excluding the ignored fields.
     *
     * @param record The data record to map.
     * @return An object of type {@code T} mapped from the data record.
     * @throws MatchException If the mapping fails.
     */
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