package fr.dl11.openmedia.data.person;

import java.time.Year;
import java.util.Optional;

public class PersonNewsData {
    private Integer challengeRank;
    private Integer forbesRank;
    private final Year year;

    public PersonNewsData(Integer challengeRank, Integer forbesRank, Year year) {
        this.challengeRank = challengeRank;
        this.forbesRank = forbesRank;
        this.year = year;
    }

    public PersonNewsData(Year year) {
        this.challengeRank = null;
        this.forbesRank = null;
        this.year = year;
    }

    public void addChallengeRank(Integer challengeRank) {
        this.challengeRank = challengeRank;
    }

    public void addForbesRank(Integer forbesRank) {
        this.forbesRank = forbesRank;
    }

    public Optional<Integer> getChallengeRank() {
        return Optional.ofNullable(challengeRank);
    }

    public Optional<Integer> getForbesRank() {
        return Optional.ofNullable(forbesRank);
    }

    public Year getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "PersonNewsData{" +
                "challengeRank=" + challengeRank +
                ", forbesRank=" + forbesRank +
                ", year=" + year +
                '}';
    }
}
