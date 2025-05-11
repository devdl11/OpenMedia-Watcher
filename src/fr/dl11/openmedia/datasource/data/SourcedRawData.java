package fr.dl11.openmedia.datasource.data;

import fr.dl11.openmedia.types.DataType;

/**
 * Represents raw data with an associated source and data type.
 *
 * <p>The {@code SourcedRawData} class extends {@link RawData} to include an additional
 * field for the source of the data. It provides constructors for creating instances
 * with or without a source, and overrides {@code equals}, {@code hashCode}, and
 * {@code toString} for proper comparison and representation.
 */
public final class SourcedRawData extends RawData {
    /**
     * The source of the raw data.
     */
    private final String source;

    /**
     * Constructs a new {@code SourcedRawData} object with the specified data and data type,
     * but without a source.
     *
     * @param data     The raw data as a string.
     * @param dataType The type of the raw data.
     */
    public SourcedRawData(String data, DataType dataType) {
        super(data, dataType);
        source = null;
    }

    /**
     * Constructs a new {@code SourcedRawData} object with the specified data, data type, and source.
     *
     * @param data     The raw data as a string.
     * @param dataType The type of the raw data.
     * @param source   The source of the raw data.
     */
    public SourcedRawData(String data, DataType dataType, String source) {
        super(data, dataType);
        this.source = source;
    }

    /**
     * Retrieves the source of the raw data.
     *
     * @return The source of the raw data, or {@code null} if not set.
     */
    public String source() {
        return source;
    }

    /**
     * Compares this {@code SourcedRawData} object to another object for equality.
     *
     * <p>Two {@code SourcedRawData} objects are considered equal if their {@code data},
     * {@code dataType}, and {@code source} fields are equal.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass() &&
                super.equals(obj) && ((SourcedRawData) obj).source != null &&
                ((SourcedRawData) obj).source.equals(source);
    }

    /**
     * Computes the hash code for this {@code SourcedRawData} object.
     *
     * @return The hash code based on the {@code data}, {@code dataType}, and {@code source} fields.
     */
    @Override
    public int hashCode() {
        return super.hashCode() + (source != null ? source.hashCode() : 0);
    }

    /**
     * Returns a string representation of this {@code SourcedRawData} object.
     *
     * <p>The string includes the values of the {@code data}, {@code dataType}, and {@code source} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "SourcedRawData[" +
                "data=" + data() + ", " +
                "dataType=" + dataType() + ", " +
                "source=" + source + ']';
    }
}