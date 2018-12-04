package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enum contains names of tokens which can appear in data
 * <p>
 * Possibile tokens are:
 * <ul>
 * <li>TAGSTART</li>
 * <li>TEXT</li>
 * <li>TAGEND</li>
 * <li>STRING</li>
 * <li>DOUBLE</li>
 * <li>INTEGER</li>
 * <li>VARIABLE</li>
 * <li>OPERATOR</li>
 * <li>FUNCTION</li>
 * <li>END</li>
 * </ul>
 *
 * @author Mihael
 */
public enum TypeToken {

    /**
     * Tag start
     */
    TAGSTART,

    /**
     * Text outside tags
     */
    TEXT,

    /**
     * Tag end
     */
    TAGEND,

    /**
     * Text between quotes in tag
     */
    STRING,

    /**
     * Double value
     */
    DOUBLE,

    /**
     * Integer value
     */
    INTEGER,

    /**
     * Mathematical operators
     */

    OPERATOR,
    /**
     * Function
     */
    FUNCTION,

    /**
     * Variable
     */
    VARIABLE,

    /**
     * End of non-empty tag
     */
    END
}
