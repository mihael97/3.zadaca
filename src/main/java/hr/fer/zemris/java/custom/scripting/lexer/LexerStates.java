package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Javni enum koji sadrzi moguca stanja rada lexera
 * 
 * @author Mihael
 *
 */
public enum LexerStates {
	/**
	 * Stanje kada lexer radi na obradi izraza koji predstavlja neku funkciju
	 */
	FUNCTION,
	/**
	 * Stanje rada kada lexer ne obraduje funkciju
	 */
	NONFUNCTION;
}
