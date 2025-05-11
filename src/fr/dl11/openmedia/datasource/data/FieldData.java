package fr.dl11.openmedia.datasource.data;

/**
 * Represents a field of data with a name and associated value.
 *
 * <p>The {@code FieldData} class is a record that encapsulates a field's name and its data.
 * It ensures that the name is neither null nor empty upon creation.
 *
 * @param name The name of the field. Must not be null or empty.
 * @param data The data associated with the field.
 * @throws IllegalArgumentException if the {@code name} is null or empty.
 */
public record FieldData(String name, String data) {

    /**
     * Validates the {@code name} field to ensure it is not null or empty.
     *
     * @throws IllegalArgumentException if the {@code name} is null or empty.
     */
    public FieldData {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    /**
     * Returns a string representation of the {@code FieldData} object.
     *
     * <p>The string includes the values of the {@code name} and {@code data} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "FieldData{" +
                "name='" + name + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}