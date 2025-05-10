package fr.dl11.openmedia.datasource.mappers.csv.components;

import fr.dl11.openmedia.common.Identifiable;
import fr.dl11.openmedia.datasource.exceptions.ElementParsingException;
import fr.dl11.openmedia.datasource.parsers.elements.IElementParser;

public record CSVColumnToken<E>(String name,
                                IElementParser<E> tokenParser) implements Identifiable, IElementParser<CSVColumnValue<E>> {
    public CSVColumnToken {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (tokenParser == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public CSVColumnValue<E> parse(String value) throws ElementParsingException {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Value cannot be null or empty");
        }
        return new CSVColumnValue<>(name, tokenParser.parse(value));
    }
}
