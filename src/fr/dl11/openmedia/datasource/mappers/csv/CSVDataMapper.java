package fr.dl11.openmedia.datasource.mappers.csv;

import fr.dl11.openmedia.datasource.data.DataRecord;
import fr.dl11.openmedia.datasource.mappers.IDataMapper;
import fr.dl11.openmedia.datasource.mappers.csv.components.CSVColumnToken;
import fr.dl11.openmedia.datasource.mappers.csv.components.CSVColumnValue;
import fr.dl11.openmedia.datasource.mappers.csv.components.CSVRowDataFactory;

import java.util.List;

public class CSVDataMapper<T> implements IDataMapper<T> {
    private final List<CSVColumnToken<?>> csvColumns;
    private final Class<T> dataClass;

    public CSVDataMapper(List<CSVColumnToken<?>> tokens, Class<T> dataClass) {
        assert tokens != null && !tokens.isEmpty();
        assert dataClass != null;

        this.csvColumns = tokens;
        this.dataClass = dataClass;
    }

    @Override
    public boolean canMap(DataRecord record) {
        assert record != null;

        return record.fields().size() == csvColumns.size() &&
                record.fields().stream().allMatch(field -> csvColumns.stream()
                        .anyMatch(token -> token.getId().equals(field.name())));
    }

    public T mapData(DataRecord dataRecord) {
        assert canMap(dataRecord);
        CSVColumnValue<?>[] csvColumnValue = new CSVColumnValue[csvColumns.size()];

        for (int i = 0; i < csvColumns.size(); i++) {
            CSVColumnToken<?> token = csvColumns.get(i);
            String fieldName = token.getId();
            String fieldValue = dataRecord.fields().stream()
                    .filter(field -> field.name().equals(fieldName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Field not found: " + fieldName))
                    .data();

            try {
                csvColumnValue[i] = token.parse(fieldValue);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing field: " + fieldName, e);
            }
        }

        return CSVRowDataFactory.createInstance(List.of(csvColumnValue), dataClass);
    }
}
