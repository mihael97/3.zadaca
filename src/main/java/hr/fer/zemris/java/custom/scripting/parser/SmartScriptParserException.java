package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Javni razred koji nasljeduje RuntimeException. Iznimka se koristi pri
 * javljaju pogreska u radu s parserom poput krivo zadanih imena
 * 
 * @author Mihael
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * SerialVersionUID zadan od strane prevoditelja
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Javni konstruktor koji prima String s opisom iznimke i prosljeduje ga
	 * nadredeno konstruktoru u razredu RuntimeException
	 * 
	 * @param exception
	 *            - niz kojeg zelimo ispisati,pojasnjenje
	 */
	public SmartScriptParserException(String exception) {
		// TODO Auto-generated constructor stub
		super(exception);
	}

	/**
	 * Zadani konstruktor,preporucljivo koristiti varijantu s ispisom
	 * problema(argument String)
	 */
	public SmartScriptParserException() {
		// TODO Auto-generated constructor stub
	}
}
