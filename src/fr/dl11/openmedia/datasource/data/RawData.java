package fr.dl11.openmedia.datasource.data;

import fr.dl11.openmedia.types.DataType;

import java.util.Objects;

/**
 * Represents raw data with an associated data type.
 *
 * <p>The {@code RawData} class encapsulates a piece of data and its corresponding
 * {@link DataType}. It provides methods to retrieve the data and its type, and
 * overrides {@code equals}, {@code hashCode}, and {@code toString} for proper
 * comparison and representation.
 */
public class RawData {
    /**
     * The raw data as a string.
     */
    private final String data;

    /**
     * The type of the raw data.
     */
    private final DataType dataType;

    /**
     * Constructs a new {@code RawData} object with the specified data and data type.
     *
     * @param data     The raw data as a string.
     * @param dataType The type of the raw data.
     */
    public RawData(String data, DataType dataType) {
        this.data = data;
        this.dataType = dataType;
    }

    /**
     * Retrieves the raw data.
     *
     * @return The raw data as a string.
     */
    public String data() {
        return data;
    }

    /**
     * Retrieves the type of the raw data.
     *
     * @return The {@link DataType} of the raw data.
     */
    public DataType dataType() {
        return dataType;
    }

    /**
     * Compares this {@code RawData} object to another object for equality.
     *
     * <p>Two {@code RawData} objects are considered equal if their {@code data}
     * and {@code dataType} fields are equal.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the objects are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RawData) obj;
        return Objects.equals(this.data, that.data) &&
                Objects.equals(this.dataType, that.dataType);
    }

    /**
     * Computes the hash code for this {@code RawData} object.
     *
     * @return The hash code based on the {@code data} and {@code dataType} fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(data, dataType);
    }

    /**
     * Returns a string representation of this {@code RawData} object.
     *
     * <p>The string includes the values of the {@code data} and {@code dataType} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "RawData[" +
                "data=" + data + ", " +
                "dataType=" + dataType + ']';
    }
}