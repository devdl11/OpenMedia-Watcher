package fr.dl11.openmedia.datasource.exceptions;

public class ElementParsingException extends ParsingException {
    public ElementParsingException(String value) {
        super("Could not parse the element. \n", value);
    }
}
