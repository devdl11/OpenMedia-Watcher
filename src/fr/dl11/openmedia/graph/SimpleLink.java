package fr.dl11.openmedia.graph;

public class SimpleLink {
    private final AbsNode source;
    private final AbsNode target;

    public SimpleLink(AbsNode source, AbsNode target) {
        this.source = source;
        this.target = target;
    }

    public AbsNode getSource() {
        return source;
    }

    public AbsNode getTarget() {
        return target;
    }
}
