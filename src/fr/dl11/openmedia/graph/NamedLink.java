package fr.dl11.openmedia.graph;

import fr.dl11.openmedia.common.TaggedElement;

public class NamedLink extends SimpleLink implements TaggedElement {
    private final String value;

    public NamedLink(AbsNode source, AbsNode target, String value) {
        super(source, target);
        this.value = value;
    }

    @Override
    public String getTag() {
        return value;
    }

    @Override
    public String toString() {
        return "NamedLink{" +
                "source=" + getSource() +
                ", target=" + getTarget() +
                ", value='" + value + '\'' +
                '}';
    }
}
