package fr.dl11.openmedia.datasource.data;

public record FieldData(String name, String data) {
    public FieldData {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    @Override
    public String toString() {
        return "FieldData{" +
                "name='" + name + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
