package fr.dl11.openmedia.datasource.parsers.elements;

import fr.dl11.openmedia.datasource.exceptions.ElementParsingException;

public interface IElementParser<T> {
    T parse(String value) throws ElementParsingException;
}
