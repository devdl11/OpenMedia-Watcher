package tests.fr.dl11.openmedia.parsing.parsers.csv;

import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.mappers.csv.components.CSVColumnToken;
import fr.dl11.openmedia.common.types.BillingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVColumnTokenTest {

    @Test
    @DisplayName("Test Token constructor")
    void CSVTokenConstruct() {
        var token = new CSVColumnToken<>("test", new EnumParser<>(BillingType.class));

        assertNotNull(token);
        assertEquals("test", token.getId());
    }

    @Test
    @DisplayName("Test Token parsing")
    void CSVTokenParse() throws Exception {
        var token = new CSVColumnToken<>("test", new EnumParser<>(BillingType.class));

        var value = token.parse("test");

        assertNotNull(value);
        assertNull(value.value());
        assertEquals("test", value.getId());
        assertEquals(BillingType.kFree, token.parse("Gratuit").value());
    }
}