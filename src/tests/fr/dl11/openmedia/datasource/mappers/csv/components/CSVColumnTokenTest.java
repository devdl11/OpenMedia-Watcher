package tests.fr.dl11.openmedia.datasource.mappers.csv.components;

import fr.dl11.openmedia.common.types.RegionType;
import fr.dl11.openmedia.datasource.exceptions.ElementParsingException;
import fr.dl11.openmedia.datasource.mappers.csv.components.CSVColumnToken;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CSVColumnTokenTest {
    static CSVColumnToken<RegionType> csvColumnToken;

    @BeforeEach
    void setUp() {
        csvColumnToken = new CSVColumnToken<>("region", new EnumParser<>(RegionType.class));
    }

    @Test
    void parse() throws ElementParsingException {
        assertThrows(IllegalArgumentException.class, () -> csvColumnToken.parse(null));
        assertThrows(IllegalArgumentException.class, () -> csvColumnToken.parse(""));
        assertNull(csvColumnToken.parse("invalid").value());

        assertEquals("region", csvColumnToken.getId());
        assertEquals(RegionType.kNational, csvColumnToken.parse("National").value());
        assertEquals(RegionType.kEurope, csvColumnToken.parse("Europe").value());
    }

}