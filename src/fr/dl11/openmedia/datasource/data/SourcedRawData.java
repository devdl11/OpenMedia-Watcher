package fr.dl11.openmedia.datasource.data;

import fr.dl11.openmedia.types.DataType;

public final class SourcedRawData extends RawData {
    private final String source;

    public SourcedRawData(String data, DataType dataType) {
        super(data, dataType);
        source = null;
    }

    public SourcedRawData(String data, DataType dataType, String source) {
        super(data, dataType);
        this.source = source;
    }

    public String source() {
        return source;
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj != null && obj.getClass() == this.getClass() &&
                super.equals(obj) && ((SourcedRawData) obj).source != null &&
                ((SourcedRawData) obj).source.equals(source);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + (source != null ? source.hashCode() : 0);
    }

    @Override
    public String toString() {
        return "SourcedRawData[]";
    }

}
