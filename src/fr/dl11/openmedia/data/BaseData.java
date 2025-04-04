package fr.dl11.openmedia.data;

import fr.dl11.openmedia.common.TaggedElement;

public class BaseData implements TaggedElement {
    private final String name;

    public BaseData(String name) {
        this.name = name;
    }

    @Override
    public String getTag() {
        return name;
    }
}
