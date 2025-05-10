package fr.dl11.openmedia.graph;

import fr.dl11.openmedia.common.Identifiable;

public class NamedLink extends SimpleLink implements Identifiable {
    private final String value;

    public NamedLink(AbsNode source, AbsNode target, String value) {
        super(source, target);
        this.value = value;
    }

    @Override
    public String getId() {
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
