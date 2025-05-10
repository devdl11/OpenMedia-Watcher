package fr.dl11.openmedia.datasource.parsers.elements;

public class EnumParser<E extends Enum<E>> implements IElementParser<E> {
    private final Class<E> enumClass;
    private final E defaultValue;

    public EnumParser(Class<E> enumClass, E defaultValue) {
        this.enumClass = enumClass;
        this.defaultValue = defaultValue;
    }

    public EnumParser(Class<E> enumClass) {
        this(enumClass, null);
    }

    public E parse(String value) {
        for (E constant : enumClass.getEnumConstants())
            if (constant.toString().equals(value)) return constant;

        return defaultValue;
    }
}
