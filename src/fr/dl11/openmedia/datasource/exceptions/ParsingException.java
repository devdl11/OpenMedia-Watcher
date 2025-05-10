package fr.dl11.openmedia.datasource.exceptions;

public class ParsingException extends Exception {
    public ParsingException(String data) {
        super("Could not parse the data. \n" +
                "Data: " + data);
    }

    public ParsingException(String details, String data) {
        super("Could not parse the data: " + details + "\n" +
                "Data: " + data);
    }

    public ParsingException(String details, String data, Throwable cause) {
        super("Could not parse the data: " + details + "\n" +
                "Data: " + data, cause);
    }
}
