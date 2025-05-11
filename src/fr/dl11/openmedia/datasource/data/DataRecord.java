package fr.dl11.openmedia.datasource.data;

import java.util.Collection;

/**
 * Represents a data record containing a collection of field data.
 *
 * <p>The {@code DataRecord} class is a record that encapsulates a collection
 * of {@link FieldData} objects, which represent individual fields within the data record.
 *
 * @param fields A collection of {@link FieldData} objects that make up the data record.
 */
public record DataRecord(Collection<FieldData> fields) {
}