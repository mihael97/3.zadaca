package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Textual data
 *
 * @author Mihael
 */
public class TextNode extends Node {
    /**
     * Text in node
     */
    private String text;

    /**
     * Constructor
     *
     * @param text input text
     */
    public TextNode(String text) {
        super();
        this.text = text;
    }

    /**
     * Returns text value
     *
     * @return String
     */
    public String getText() {
        return prepareForOutput();
    }

    /**
     * Converts values of elements in parser acceptable format
     *
     * @return node value in parser acceptable format
     */
    public String prepareForOutput() {
        StringBuilder builder = new StringBuilder();
        char[] array = text.toCharArray();

        for (int i = 0; i < array.length; i++) {
            if (array[i] == '\\') {
                builder.append( array[i] );
            } else if (array[i] == '{') {
                builder.append( "\\" );
            }

            builder.append( array[i] );
        }

        return builder.toString();
    }

}
