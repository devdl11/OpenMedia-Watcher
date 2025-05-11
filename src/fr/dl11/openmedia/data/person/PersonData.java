package fr.dl11.openmedia.data.person;

import java.time.Year;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents data related to a person.
 *
 * <p>The {@code PersonData} class is used to store and manage information about a person,
 * including their name and associated news data for specific years.
 */
public class PersonData {
    /**
     * The name of the person.
     */
    private final String name;

    /**
     * A map containing news data associated with specific years.
     */
    private final Map<Year, PersonNewsData> newsData;

    /**
     * Constructs a new {@code PersonData} object with default values.
     *
     * <p>The {@code name} field is initialized to {@code null}, and the {@code newsData}
     * map is initialized as an empty {@link HashMap}.
     */
    PersonData() {
        name = null;
        newsData = new HashMap<>();
    }

    /**
     * Constructs a new {@code PersonData} object with the specified name.
     *
     * <p>The {@code newsData} map is initialized as an empty {@link HashMap}.
     *
     * @param name The name of the person.
     */
    public PersonData(String name) {
        this.name = name;
        newsData = new HashMap<>();
    }

    /**
     * Constructs a new {@code PersonData} object with the specified name and news data.
     *
     * @param name     The name of the person.
     * @param newsData A map containing news data associated with specific years.
     */
    public PersonData(String name, Map<Year, PersonNewsData> newsData) {
        this.name = name;
        this.newsData = newsData;
    }

    /**
     * Retrieves the name of the person.
     *
     * @return The name of the person.
     */
    public String getName() {
        return name;
    }

    /**
     * Adds news data for a specific year.
     *
     * @param data The {@link PersonNewsData} object to add.
     */
    public void addNewsData(PersonNewsData data) {
        newsData.put(data.getYear(), data);
    }

    /**
     * Retrieves all news data associated with the person.
     *
     * @return A collection of {@link PersonNewsData} objects.
     */
    public Collection<PersonNewsData> getNewsData() {
        return newsData.values();
    }

    /**
     * Returns a string representation of the {@code PersonData} object.
     *
     * <p>The string includes the values of the {@code name} and {@code newsData} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "PersonData{" +
                "name='" + name + '\'' +
                ", newsData=" + newsData +
                '}';
    }
}