package hr.fer.zemris.java.hw03.prob1;

/**
 * Javni razred koji implementira token
 * @author Mihael
 *
 */
public class Token {
	/**
	 * Referenca na tip tokena
	 */
	TokenType type;
	/**
	 * Referenca na vrijednost tokena
	 */
	Object value;

	/**
	 * Javni konstruktor koji stvara token
	 * 
	 * @param type
	 *            - vrsta tokena
	 * @param value
	 *            - vrijednost
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Vraca vrijednost tokena
	 * 
	 * @return vrijednost
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Vraca vrstu tokena
	 * 
	 * @return tip tokena
	 */
	public TokenType getType() {
		return type;
	}
}
