package fr.dl11.openmedia.data;

import java.time.Year;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PersonData extends BaseData {
    private final Map<Year, PersonNewsData> newsData;

    public PersonData(String name) {
        super(name);
        newsData = new HashMap<>();
    }

    public PersonData(String name, Map<Year, PersonNewsData> newsData) {
        super(name);
        this.newsData = newsData;
    }

    public void addNewsData(PersonNewsData data) {
        newsData.put(data.year(), data);
    }

    public Collection<PersonNewsData> getNewsData() {
        return newsData.values();
    }
}
