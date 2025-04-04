package fr.dl11.openmedia.graph;

import fr.dl11.openmedia.types.NodeType;

public abstract class AbsNode {
    private final NodeType type;
    private final String name;

    protected AbsNode(NodeType type, String name) {
        this.type = type;
        this.name = name;
    }

    public NodeType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "AbsNode{" +
                "type=" + type +
                ", name='" + name + '\'' +
                '}';
    }
}
