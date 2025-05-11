package fr.dl11.openmedia.data.persons;

import fr.dl11.openmedia.common.types.EqualityType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

public class PersonMediaLinkData {
    @BindToKey(key = "origine", parser = StringParser.class)
    private final String origin;

    @BindToKey(key = "qualificatif", parser = EnumParser.class)
    private final EqualityType equalityType;

    @BindToKey(key = "valeur", parser = StringParser.class)
    private final String value;

    @BindToKey(key = "cible", parser = StringParser.class)
    private final String target;

    PersonMediaLinkData() {
        this.origin = null;
        this.equalityType = null;
        this.value = null;
        this.target = null;
    }
}
