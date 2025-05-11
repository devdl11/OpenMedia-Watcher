package fr.dl11.openmedia.types;

/**
 * Enumeration representing different types of data.
 *
 * <p>The {@code DataType} enum defines constants for various data types
 * that can be processed in the application. It includes predefined types
 * such as {@code CSV}, {@code ARTICLE}, and a fallback type {@code UNSUPPORTED}
 * for unrecognized data formats.</p>
 */
public enum DataType {
    /**
     * Represents data in CSV (Comma-Separated Values) format.
     */
    CSV,

    /**
     * Represents data in the form of an article.
     */
    ARTICLE,

    /**
     * Represents unsupported or unrecognized data types.
     */
    UNSUPPORTED,
}