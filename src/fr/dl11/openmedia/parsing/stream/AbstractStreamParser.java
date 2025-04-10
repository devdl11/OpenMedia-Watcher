package fr.dl11.openmedia.parsing.stream;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

public abstract class AbstractStreamParser {
    protected final BufferedReader reader;

    public AbstractStreamParser(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public abstract List<StreamFragment> parse() throws Exception;
}
