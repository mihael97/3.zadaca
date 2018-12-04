package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Javni enum koji sadrzi moguca stanja rada lexera(TAG i NONTAG)
 * Enum for lexer's states
 *
 * @author Mihael
 */
public enum LexerStates {

    /**
     * Inside tags
     */
    TAG,

    /**
     * Outside tags
     */
    NONTAG;

}
