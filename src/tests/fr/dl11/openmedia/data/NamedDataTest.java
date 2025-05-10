package tests.fr.dl11.openmedia.data;

import fr.dl11.openmedia.datasource.mappers.csv.components.CSVColumnValue;
import fr.dl11.openmedia.datasource.mappers.csv.components.CSVRowDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class NamedDataTest {

    @Test
    @DisplayName("Test the creation of BaseData from CSVTokens")
    void createInstanceFromCSV() {
        var array = new ArrayList<CSVColumnValue<?>>();
        array.add(new CSVColumnValue<>("nom", "test"));
    }
}