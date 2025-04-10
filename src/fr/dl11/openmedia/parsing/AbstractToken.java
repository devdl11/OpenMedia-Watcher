package fr.dl11.openmedia.parsing;

import java.lang.reflect.Modifier;

public abstract class AbstractToken {
    public final String keyword;

    protected AbstractToken(String keyword) {
        this.keyword = keyword;
    }

    public boolean matches(String keyword) {
        return this.keyword.equalsIgnoreCase(keyword);
    }

    public AbstractToken fromString(String keyword) throws IllegalArgumentException {
        for (var element: this.getClass().getDeclaredFields()) {
            if (Modifier.isStatic(element.getModifiers()) && element.getType() == this.getClass()) {
                try {
                    AbstractToken parsable = (AbstractToken) element.get(null);
                    if (parsable.matches(keyword)) {
                        return parsable;
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        throw new IllegalArgumentException("Invalid " + this.getClass().getSimpleName() + ": " + keyword);
    }
}
