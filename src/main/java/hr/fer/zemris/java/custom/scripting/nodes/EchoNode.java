package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

import java.util.Arrays;

/**
 * Echo node
 *
 * @author Mihael
 */
public class EchoNode extends Node {
    /**
     * Elements
     */
    Element[] elements;

    /**
     * Constrcutor
     *
     * @param elements elements
     */
    public EchoNode(Element[] elements) {
        super();
        this.elements = Arrays.copyOf( elements, elements.length );
    }

    /**
     * Returns elements
     *
     * @return elements
     */
    public Element[] getElements() {
        return elements;
    }

    /**
     * Returns String representation of EchoNode
     *
     * @return string string representation
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder().append( "{$= " );

        for (Element ele : elements) {
            if (ele != null) {
                if (ele instanceof ElementString) {
                    string.append( ((ElementString) ele).forParse() + " " );
                } else {
                    string.append( ele.asText() + " " );
                }
            }
        }

        string.append( "$}" );

        return string.toString();
    }
}
