package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Token class
 *
 * @author Mihael
 */
public class Token {

    /**
     * Token type
     */
    private final TypeToken type;
    /**
     * Token value
     */
    private final Object value;

    /**
     * Constructor
     *
     * @param type  type
     * @param value value
     */
    public Token(TypeToken type, Object value) {
        super();
        this.type = type;
        this.value = value;
    }

    /**
     * Returns token's type
     *
     * @return {@link TypeToken}
     */
    public TypeToken getType() {
        return type;
    }

    /**
     * Token's value
     *
     * @return token's value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Token's String representation
     *
     * @return string representation
     */
    @Override
    public String toString() {
        if (value instanceof Integer || value instanceof Double)
            return value.toString();
        else {
            return (String) value;
        }
    }

}
