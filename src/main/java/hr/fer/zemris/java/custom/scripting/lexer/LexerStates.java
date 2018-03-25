package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Javni enum koji sadrzi moguca stanja rada lexera
 * 
 * @author Mihael
 *
 */
public enum LexerStates {
	/**
	 * Stanje kada lexer obraduje for petlju
	 */
	FORLOOP,

	/**
	 * Stanje kada lexer obraduje naredbu koja generira neki ispis dinamicki
	 */
	ECHO,
	/**
	 * Stanje rada kada lexer ne obraduje nikakav funkcijski izraz nego promatra
	 * samo Stringove
	 */
	NONTAG;

}
