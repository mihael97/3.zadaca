package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Double value element
 *
 * @author Mihael
 */
public class ElementConstantDouble extends Element {
    /**
     * Double value
     */
    private double value;

    /**
     * Constructor
     *
     * @param value double value
     */
    public ElementConstantDouble(double value) {
        this.value = value;
    }

    /**
     * Returns value in String format
     *
     * @return String string format
     */
    @Override

    public String asText() {
        return Double.valueOf( value ).toString();
    }
}
