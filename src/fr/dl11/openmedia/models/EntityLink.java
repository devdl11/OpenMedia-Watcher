package fr.dl11.openmedia.models;

import fr.dl11.openmedia.common.types.EqualityType;

public record EntityLink<S, T>(S source, T target, String value, EqualityType equalityType) {

    public boolean isLinkBetween(S source, T target) {
        return this.source.equals(source) && this.target.equals(target);
    }
}
