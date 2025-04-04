package fr.dl11.openmedia.parsing;

public interface ITokenParser<T> {
    T parse(String token) throws Exception;
}
