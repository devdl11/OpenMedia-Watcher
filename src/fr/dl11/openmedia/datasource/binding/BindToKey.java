package fr.dl11.openmedia.datasource.binding;

import fr.dl11.openmedia.datasource.parsers.elements.IElementParser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// https://dev.java/learn/annotations/
// https://dev.java/learn/reflection/annotations/
/**
 * Annotation to bind a field to a specific key in a data source.
 *
 * <p>This annotation can be applied to fields in a class to specify
 * the key reference in a data source that the field corresponds to.
 *
 * <p>Usage example:
 * <pre>
 * {@code
 * @BindToKey(key = "key_name")
 * private String fieldName;
 * }
 * </pre>
 *
 * @Target(ElementType.FIELD) Indicates that this annotation can only be applied to fields.
 * @Retention(RetentionPolicy.RUNTIME) Ensures the annotation is available at runtime for reflection.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindToKey {
    String key();

    @SuppressWarnings("rawtypes")
    Class<? extends IElementParser> parser();
}
