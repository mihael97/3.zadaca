package hr.fer.zemris.java.custom.scripting.lexer;

/**
 *Lexer exception
 *
 * @author Mihael
 */
public class LexerException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param string exception message
     */
    public LexerException(String string) {
        super( string );
    }
}
