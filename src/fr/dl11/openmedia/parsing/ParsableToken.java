package fr.dl11.openmedia.parsing;

public class ParsableToken<E extends Enum<E>> implements ITokenParser<E> {
    private final Class<E> enumClass;
    private final E defaultValue;

    ParsableToken(Class<E> enumClass, E defaultValue) {
        this.enumClass = enumClass;
        this.defaultValue = defaultValue;
    }

    ParsableToken(Class<E> enumClass) {
        this(enumClass, null);
    }

    public E parse(String token) throws Exception {
        try {
            for (E constant : enumClass.getEnumConstants())
                if (constant.name().equals(token)) return constant;
        } catch (Exception e) {
            // Handle exception
            e.printStackTrace();
        }

        if (defaultValue != null) return defaultValue;
        throw new Exception("azeaze");
    }
}
