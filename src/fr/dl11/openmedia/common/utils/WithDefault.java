package fr.dl11.openmedia.common.utils;

/**
 * Interface representing a type that provides a default value.
 *
 * <p>This interface is designed to be implemented by classes or enums
 * that need to specify a default instance of their type.
 *
 * @param <T> the type of the implementing class or enum.
 */
public interface WithDefault<T> {
    /**
     * Returns the default instance of the implementing type.
     *
     * @return the default instance of type {@code T}.
     */
    T getDefault();
}
