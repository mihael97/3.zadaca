package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Constant string value
 *
 * @author Mihael
 */
public class ElementString extends Element {
    /**
     * Constant string value
     */
    private String value;

    /**
     * Constructor
     *
     * @param value value
     * @throws NullPointerException if value is null
     */
    public ElementString(String value) {
        if (value == null) {
            throw new NullPointerException( "Vrijednost je null!" );
        }
        this.value = value;

    }

    /**
     * Returns value
     *
     * @return String value
     */
    @Override
    public String asText() {
        return value;
    }

    /**
     * Converts string value in parser acceptable format
     *
     * @return String in parser acceptable format
     */
    public String forParse() {
        char[] array = value.toCharArray();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < array.length; i++) {
            if (array[i] == '\\' || array[i] == '\"' && i != 0 && i != (array.length - 1)) {
                builder.append( "\\" );
            }

            builder.append( array[i] );
        }

        return builder.toString();
    }

}
