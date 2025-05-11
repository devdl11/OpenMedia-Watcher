package fr.dl11.openmedia.models;

import fr.dl11.openmedia.common.types.EqualityType;

/**
 * Represents a link between two entities.
 *
 * <p>The {@code EntityLink} record is a generic data structure that models a relationship
 * between a source entity and a target entity. It also includes a value representing
 * the link and an {@link EqualityType} to describe the type of equality between the entities.</p>
 *
 * @param <S>          The type of the source entity.
 * @param <T>          The type of the target entity.
 * @param source       The source entity in the link. Must not be null.
 * @param target       The target entity in the link. Must not be null.
 * @param value        A string value representing the link. Can be null.
 * @param equalityType The type of equality between the source and target entities. Must not be null.
 */
public record EntityLink<S, T>(S source, T target, String value, EqualityType equalityType) {

    /**
     * Checks if this link is between the specified source and target entities.
     *
     * @param source The source entity to check. Must not be null.
     * @param target The target entity to check. Must not be null.
     * @return {@code true} if this link is between the specified source and target entities,
     * {@code false} otherwise.
     */
    public boolean isLinkBetween(S source, T target) {
        return this.source.equals(source) && this.target.equals(target);
    }
}
