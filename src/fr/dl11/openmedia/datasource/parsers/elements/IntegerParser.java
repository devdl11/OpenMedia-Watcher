package fr.dl11.openmedia.datasource.parsers.elements;

import fr.dl11.openmedia.datasource.exceptions.ElementParsingException;

public class IntegerParser implements IElementParser<Integer> {
    @Override
    public Integer parse(String data) throws ElementParsingException {
        try {
            return Integer.parseInt(data);
        } catch (NumberFormatException e) {
            throw new ElementParsingException("Invalid integer format: " + data);
        }
    }
}
