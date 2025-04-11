package tests.fr.dl11.openmedia.data;

import fr.dl11.openmedia.data.BaseData;
import fr.dl11.openmedia.parsing.parsers.csv.CSVColumnValue;
import fr.dl11.openmedia.parsing.parsers.csv.CSVRowDataFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BaseDataTest {

    @Test
    @DisplayName("Test the creation of BaseData from CSVTokens")
    void createInstanceFromCSV() {
        var array = new ArrayList<CSVColumnValue<?>>();
        array.add(new CSVColumnValue<>("nom", "test"));

        var data = CSVRowDataFactory.createInstance(Collections.unmodifiableCollection(array), BaseData.class);

        assertNotNull(data);
        assertTrue(data instanceof BaseData);
        assertEquals("test", ((BaseData) data).getTag());
    }
}