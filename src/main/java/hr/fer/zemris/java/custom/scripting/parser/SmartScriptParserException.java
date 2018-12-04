package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Parser exception
 *
 * @author Mihael
 */
public class SmartScriptParserException extends RuntimeException {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param exp exception message
     */
    public SmartScriptParserException(String exp) {
        super( exp );
    }

    /**
     * Constructor
     *
     * @param message exception message
     * @param cause   cause exception
     */
    public SmartScriptParserException(String message, Throwable cause) {
        super( message, cause );
    }

}
