package fr.dl11.openmedia.data;

import fr.dl11.openmedia.common.types.PublicationType;
import fr.dl11.openmedia.datasource.binding.BindToKey;
import fr.dl11.openmedia.datasource.parsers.elements.EnumParser;
import fr.dl11.openmedia.datasource.parsers.elements.StringParser;

public class PublicationData {
    @BindToKey(key = "type", parser = EnumParser.class)
    private final PublicationType type;

    @BindToKey(key = "title", parser = StringParser.class)
    private final String title;

    @BindToKey(key = "content", parser = StringParser.class)
    private final String content;

    @BindToKey(key = "author", parser = StringParser.class)
    private final String author;

    @BindToKey(key = "mediaName", parser = StringParser.class)
    private final String mediaName;

    public PublicationData() {
        this.type = PublicationType.kArticle;
        this.title = null;
        this.content = null;
        this.author = null;
        this.mediaName = null;
    }

    public PublicationType getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String getMediaName() {
        return mediaName;
    }

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
