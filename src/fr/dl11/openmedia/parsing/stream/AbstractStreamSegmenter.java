package fr.dl11.openmedia.parsing.stream;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.List;

public abstract class AbstractStreamSegmenter {
    protected final BufferedReader reader;

    public AbstractStreamSegmenter(Reader reader) {
        this.reader = new BufferedReader(reader);
    }

    public abstract List<StreamFragment> segment() throws Exception;
}
