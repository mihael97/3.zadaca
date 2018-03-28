package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeracija koja propisuje moguca stanja
 * @author Mihael
 *
 */
public enum LexerState {
	/**
	 * Osnovni nacin rada
	 */
	BASIC,
	/**
	 * Napredni nacin rada gdje lexer sve unutar znakova '#' smatram tekstom
	 */
	EXTENDED;
}
