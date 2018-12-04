package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Variable with name
 *
 * @author Mihael
 */
public class ElementVariable extends Element {
    /**
     * Variable name
     */
    private String name;

    /**
     * Construcor
     *
     * @param name name
     * @throws NullPointerException if name is null
     */
    public ElementVariable(String name) {
        if (name == null) {
            throw new NullPointerException( "Ime ne smije biti null" );
        } else {
            this.name = name;
        }
    }

    /**
     * Returns variable name
     *
     * @return String
     */
    @Override
    public String asText() {
        return name;
    }

}
