package fr.dl11.openmedia.datasource.mappers;

import fr.dl11.openmedia.datasource.data.DataRecord;

import java.util.Collection;

/**
 * A data mapper that associates specific sources with the mapping process.
 *
 * <p>The {@code SourcedDataMapper} class implements {@link IDataMapper} and
 * provides functionality to map data records to objects of type {@code T},
 * while also considering a collection of allowed sources. This allows for
 * source-based filtering during the mapping process.
 *
 * @param <T> The type of object to map the data to.
 */
public class SourcedDataMapper<T> implements IDataMapper<T> {

    /**
     * The underlying data mapper used for mapping operations.
     */
    private final IDataMapper<T> mapper;

    /**
     * A collection of allowed sources for mapping.
     */
    private final Collection<String> sources;

    /**
     * Constructs a new {@code SourcedDataMapper} with the specified data mapper
     * and collection of sources.
     *
     * @param mapper  The underlying data mapper to use for mapping operations.
     * @param sources A collection of allowed sources for mapping.
     */
    public SourcedDataMapper(IDataMapper<T> mapper, Collection<String> sources) {
        this.mapper = mapper;
        this.sources = sources;
    }

    /**
     * Checks if the given {@link DataRecord} can be mapped using the underlying mapper.
     *
     * @param record The data record to check.
     * @return {@code true} if the record can be mapped, {@code false} otherwise.
     */
    @Override
    public boolean canMap(DataRecord record) {
        return mapper.canMap(record);
    }

    /**
     * Checks if the given {@link DataRecord} can be mapped from a specific source.
     *
     * @param record The data record to check.
     * @param source The source to validate against the allowed sources.
     * @return {@code true} if the source is allowed and the record can be mapped, {@code false} otherwise.
     */
    public boolean canMap(DataRecord record, String source) {
        return sources.contains(source) && mapper.canMap(record);
    }

    /**
     * Maps the given {@link DataRecord} to an object of type {@code T} using the underlying mapper.
     *
     * @param record The data record to map.
     * @return An object of type {@code T} mapped from the data record.
     * @throws MatchException If the mapping fails.
     */
    @Override
    public T mapData(DataRecord record) throws MatchException {
        return mapper.mapData(record);
    }
}