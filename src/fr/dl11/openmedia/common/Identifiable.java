package fr.dl11.openmedia.common;

/**
 * Interface representing an identifiable entity.
 *
 * <p>This interface is designed to be implemented by classes that need to
 * provide a unique identifier for their instances.
 */
public interface Identifiable {
    /**
     * Returns the unique identifier of the entity.
     *
     * @return the unique identifier as a {@code String}.
     */
    String getId();
}
