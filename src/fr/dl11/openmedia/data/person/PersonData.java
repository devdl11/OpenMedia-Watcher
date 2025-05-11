package fr.dl11.openmedia.data.person;

import java.time.Year;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PersonData {
    private final String name;
    private final Map<Year, PersonNewsData> newsData;

    PersonData() {
        name = null;
        newsData = new HashMap<>();
    }

    public PersonData(String name) {
        this.name = name;
        newsData = new HashMap<>();
    }

    public PersonData(String name, Map<Year, PersonNewsData> newsData) {
        this.name = name;
        this.newsData = newsData;
    }

    public String getName() {
        return name;
    }

    public void addNewsData(PersonNewsData data) {
        newsData.put(data.getYear(), data);
    }

    public Collection<PersonNewsData> getNewsData() {
        return newsData.values();
    }

    @Override
    public String toString() {
        return "PersonData{" +
                "name='" + name + '\'' +
                ", newsData=" + newsData +
                '}';
    }
}
