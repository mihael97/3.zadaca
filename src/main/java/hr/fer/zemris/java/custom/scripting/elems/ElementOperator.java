package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Operator
 *
 * @author Mihael
 */
public class ElementOperator extends Element {
    /**
     * Operator
     */
    private String symbol;

    /**
     * Constructor
     *
     * @param value value
     * @throws NullPointerException if value is null
     */
    public ElementOperator(String value) {
        if (value == null) {
            throw new NullPointerException( "Vrijednost je null!" );
        }
        this.symbol = value;
    }

    /**
     * Returns value
     *
     * @return String value
     */
    @Override
    public String asText() {
        return symbol;
    }
}
