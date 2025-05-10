package fr.dl11.openmedia.datasource.mappers.csv.components;

import fr.dl11.openmedia.common.Identifiable;

public record CSVColumnValue<E>(String name, E value) implements Identifiable {
    public CSVColumnValue {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
    }

    public String getId() {
        return name;
    }

    @Override
    public String toString() {
        return "CSVColumnValue{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
