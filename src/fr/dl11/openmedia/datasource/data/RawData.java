package fr.dl11.openmedia.datasource.data;

import fr.dl11.openmedia.types.DataType;

import java.util.Objects;

public class RawData {
    private final String data;
    private final DataType dataType;

    public RawData(String data, DataType dataType) {
        this.data = data;
        this.dataType = dataType;
    }

    public String data() {
        return data;
    }

    public DataType dataType() {
        return dataType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RawData) obj;
        return Objects.equals(this.data, that.data) &&
                Objects.equals(this.dataType, that.dataType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, dataType);
    }

    @Override
    public String toString() {
        return "RawData[" +
                "data=" + data + ", " +
                "dataType=" + dataType + ']';
    }

}
