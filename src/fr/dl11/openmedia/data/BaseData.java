package fr.dl11.openmedia.data;

import fr.dl11.openmedia.common.TaggedElement;
import fr.dl11.openmedia.parsing.parsers.csv.CSVBindColumn;
import fr.dl11.openmedia.parsing.parsers.csv.CSVColumnValue;

import java.util.Collection;

public class BaseData implements TaggedElement {
    @CSVBindColumn(ref = "nom")
    private final String name;

    public BaseData() {
        this.name = null;
    }

    public BaseData(String name) {
        this.name = name;
    }

    @Override
    public String getTag() {
        return name;
    }
}
