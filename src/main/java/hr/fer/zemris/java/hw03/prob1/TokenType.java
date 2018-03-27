package hr.fer.zemris.java.hw03.prob1;

/**
 * Javni enum smogucim tipovima podataka
 * 
 * @author ime
 *
 */
public enum TokenType {
	/**
	 * Oznaka koja predstavlja token za kraj datoteke
	 */
	EOF,

	/**
	 * Oznaka tokena koji predstavlja rijec
	 */
	WORD,

	/**
	 * Oznaka tokena koja predstavlja broj(cijelobrojni ili decimalni)
	 */
	NUMBER,

	/**
	 * Oznaka tokena koja predstavlja simbol
	 */
	SYMBOL
}
