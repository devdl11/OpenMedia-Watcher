package fr.dl11.openmedia.data.person;

import java.time.Year;
import java.util.Optional;

/**
 * Represents news data related to a person for a specific year.
 *
 * <p>The {@code PersonNewsData} class is used to store and manage rankings
 * (e.g., Challenge and Forbes rankings) associated with a person for a given year.
 */
public class PersonNewsData {

    /**
     * The Challenge ranking of the person.
     */
    private Integer challengeRank;

    /**
     * The Forbes ranking of the person.
     */
    private Integer forbesRank;

    /**
     * The year associated with the news data.
     */
    private final Year year;

    /**
     * Constructs a new {@code PersonNewsData} object with the specified rankings and year.
     *
     * @param challengeRank The Challenge ranking of the person.
     * @param forbesRank    The Forbes ranking of the person.
     * @param year          The year associated with the news data.
     */
    public PersonNewsData(Integer challengeRank, Integer forbesRank, Year year) {
        this.challengeRank = challengeRank;
        this.forbesRank = forbesRank;
        this.year = year;
    }

    /**
     * Constructs a new {@code PersonNewsData} object with the specified year.
     *
     * <p>The {@code challengeRank} and {@code forbesRank} fields are initialized to {@code null}.
     *
     * @param year The year associated with the news data.
     */
    public PersonNewsData(Year year) {
        this.challengeRank = null;
        this.forbesRank = null;
        this.year = year;
    }

    /**
     * Adds or updates the Challenge ranking of the person.
     *
     * @param challengeRank The Challenge ranking to set.
     */
    public void addChallengeRank(Integer challengeRank) {
        this.challengeRank = challengeRank;
    }

    /**
     * Adds or updates the Forbes ranking of the person.
     *
     * @param forbesRank The Forbes ranking to set.
     */
    public void addForbesRank(Integer forbesRank) {
        this.forbesRank = forbesRank;
    }

    /**
     * Retrieves the Challenge ranking of the person.
     *
     * @return An {@link Optional} containing the Challenge ranking, or an empty {@link Optional} if not set.
     */
    public Optional<Integer> getChallengeRank() {
        return Optional.ofNullable(challengeRank);
    }

    /**
     * Retrieves the Forbes ranking of the person.
     *
     * @return An {@link Optional} containing the Forbes ranking, or an empty {@link Optional} if not set.
     */
    public Optional<Integer> getForbesRank() {
        return Optional.ofNullable(forbesRank);
    }

    /**
     * Retrieves the year associated with the news data.
     *
     * @return The year associated with the news data.
     */
    public Year getYear() {
        return year;
    }

    /**
     * Returns a string representation of the {@code PersonNewsData} object.
     *
     * <p>The string includes the values of the {@code challengeRank}, {@code forbesRank}, and {@code year} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "PersonNewsData{" +
                "challengeRank=" + challengeRank +
                ", forbesRank=" + forbesRank +
                ", year=" + year +
                '}';
    }
}