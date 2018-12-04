package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Function
 *
 * @author Mihael
 */
public class ElementFunction extends Element {
    /**
     * Function name
     */
    private String name;

    /**
     * Constrcutor
     *
     * @param value function name
     * @throws NullPointerException if name is null
     */
    public ElementFunction(String value) {
        if (value == null) {
            throw new NullPointerException( "Vrijednost je null!" );
        }
        this.name = value;
    }

    /**
     * Returns function in String format
     *
     * @return String string format
     */
    @Override
    public String asText() {
        return "@" + name;
    }
}
