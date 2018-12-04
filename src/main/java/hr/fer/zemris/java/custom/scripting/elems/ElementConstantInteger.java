package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Integer element
 *
 * @author Mihael
 */
public class ElementConstantInteger extends Element {
    /**
     * Integer value
     */
    private int value;

    /**
     * Constructor
     *
     * @param value value
     */
    public ElementConstantInteger(int value) {
        this.value = value;
    }

    /**
     * Returns value in String format
     *
     * @return String
     */
    @Override
    public String asText() {
        return Integer.valueOf( value ).toString();
    }

}
