package fr.dl11.openmedia.datasource.parsers.elements;

public class BooleanParser implements IElementParser<Boolean> {
    @Override
    public Boolean parse(String data) {
        return Boolean.parseBoolean(data);
    }
}
