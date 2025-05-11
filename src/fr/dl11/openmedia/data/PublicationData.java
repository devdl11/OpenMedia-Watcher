package fr.dl11.openmedia.data;

import fr.dl11.openmedia.common.types.PublicationType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

/**
 * Represents data related to a publication.
 *
 * <p>The {@code PublicationData} class is used to store and retrieve information
 * about a publication, including its type, title, content, author, and associated media name.
 * The fields are bound to specific keys in a data source using the {@link BindToKey} annotation.
 */
public class PublicationData {

    /**
     * The type of the publication.
     *
     * <p>This field is bound to the key "type" in the data source and is parsed
     * using the {@link EnumParser} class.
     */
    @BindToKey(key = "type", parser = EnumParser.class)
    private final PublicationType type;

    /**
     * The title of the publication.
     *
     * <p>This field is bound to the key "title" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "title", parser = StringParser.class)
    private final String title;

    /**
     * The content of the publication.
     *
     * <p>This field is bound to the key "content" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "content", parser = StringParser.class)
    private final String content;

    /**
     * The author of the publication.
     *
     * <p>This field is bound to the key "author" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "author", parser = StringParser.class)
    private final String author;

    /**
     * The name of the media associated with the publication.
     *
     * <p>This field is bound to the key "mediaName" in the data source and is parsed
     * using the {@link StringParser} class.
     */
    @BindToKey(key = "mediaName", parser = StringParser.class)
    private final String mediaName;

    /**
     * Constructs a new {@code PublicationData} object with default values.
     *
     * <p>The {@code type} field is initialized to {@code PublicationType.kArticle},
     * and the {@code title}, {@code content}, {@code author}, and {@code mediaName}
     * fields are initialized to {@code null}.
     */
    public PublicationData() {
        this.type = PublicationType.kArticle;
        this.title = null;
        this.content = null;
        this.author = null;
        this.mediaName = null;
    }

    /**
     * Retrieves the type of the publication.
     *
     * @return The type of the publication.
     */
    public PublicationType getType() {
        return type;
    }

    /**
     * Retrieves the title of the publication.
     *
     * @return The title of the publication, or {@code null} if not set.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Retrieves the content of the publication.
     *
     * @return The content of the publication, or {@code null} if not set.
     */
    public String getContent() {
        return content;
    }

    /**
     * Retrieves the author of the publication.
     *
     * @return The author of the publication, or {@code null} if not set.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Retrieves the name of the media associated with the publication.
     *
     * @return The name of the media, or {@code null} if not set.
     */
    public String getMediaName() {
        return mediaName;
    }

    /**
     * Returns a string representation of the {@code PublicationData} object.
     *
     * <p>The string includes the values of the {@code type}, {@code title},
     * {@code content}, and {@code author} fields.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "PublicationData{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}