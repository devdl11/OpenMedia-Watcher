package fr.dl11.openmedia.parsing;

public class NewsType extends AbsParsable {
    public static final NewsType kTV = new NewsType("Télévision");
    public static final NewsType kRadio = new NewsType("Radio");
    public static final NewsType kWeb = new NewsType("Site");
    public static final NewsType kGen = new NewsType("Presse (généraliste politique économique)");
    public static final NewsType kNone = new NewsType("Aucun");


    NewsType(String keyword) {
        super(keyword);
    }

    @Override
    public NewsType fromString(String keyword) throws IllegalArgumentException {
        try {
            return (NewsType)super.fromString(keyword);
        } catch (IllegalArgumentException e) {
            return kNone;
        }
    }
}
