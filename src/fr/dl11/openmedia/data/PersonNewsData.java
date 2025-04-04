package fr.dl11.openmedia.data;

import java.time.Year;
import java.util.Optional;

public record PersonNewsData(
        Optional<Integer> challengeRank,
        Optional<Integer> forbesRank,
        Year year
) {
}
