package fr.dl11.openmedia.graph;

public class NamedLink extends SimpleLink {
    private String value;

    public NamedLink(AbsNode source, AbsNode target, String value) {
        super(source, target);
        this.value = value;
    }

    public String getValue() {
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
