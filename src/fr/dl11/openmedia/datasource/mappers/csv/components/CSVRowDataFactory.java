package fr.dl11.openmedia.datasource.mappers.csv.components;

import fr.dl11.openmedia.datasource.binding.BindToKey;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CSVRowDataFactory {
    private CSVRowDataFactory() {}

    public static <T> T createInstance(Collection<CSVColumnValue<?>> columns, Class<T> dest) {
        Map<String, Field> classFields = new HashMap<>();

        Arrays.stream(dest.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(BindToKey.class))
                .forEach(field -> {
                    BindToKey bindColumn = field.getAnnotation(BindToKey.class);
                    assert bindColumn != null;
                    classFields.put(bindColumn.key().toLowerCase(), field);
                });

        if (classFields.isEmpty()) return null;

        T instance;

        try {
            instance = dest.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        for (CSVColumnValue<?> column : columns) {
            var field = classFields.get(column.getId().toLowerCase());
            if (field == null) continue;

            System.out.println(field.getName() + " is of type " + field.getType().getName() + " and value is of type " + column.value().getClass().getName());
            if (!field.getType().isAssignableFrom(column.value().getClass())) {
                throw new IllegalArgumentException("Field " + field.getName() + " is not of type " + column.value().getClass().getName());
            }

            try {
                boolean canAccess = field.canAccess(instance);
                if (!canAccess) field.setAccessible(true);

                field.set(instance, column.value());
                classFields.remove(column.getId());

                if (!canAccess) field.setAccessible(false);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Failed to set field " + field.getName() + " with value " + column.value(), e);
            }
        }

        if (!classFields.isEmpty()) {
            StringBuilder sb = new StringBuilder("The following fields were not set: ");
            classFields.forEach((key, value) -> sb.append(key).append(", "));
            sb.setLength(sb.length() - 2); // Remove the last comma and space
            throw new IllegalArgumentException(sb.toString());
        }

        return instance;
    }
}
