package fr.dl11.openmedia.parsing.stream;

import java.util.*;

public class StreamFragment {
    private final List<String> elements;

    StreamFragment() {
        this.elements = new LinkedList<>();
    }

    public void addElement(String element) {
        elements.add(element);
    }

    public void addElements(Collection<String> elements) {
        this.elements.addAll(elements);
    }

    public Collection<String> getElements() {
        return Collections.unmodifiableCollection(elements);
    }
}
