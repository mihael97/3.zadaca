package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Razred koji implementira lexer kojim prozivodimo tokene koje kasnije
 * koristimo za stvaranje elemenata,a kasnije i cvorova. Konstruktoru je za
 * inicijalizaciju potreban String koji se pretvara u polje znakova kroz koje
 * lexer prolazi i generira leksicke jedinice ovisno o nacinu rada i ostalim
 * pravilima
 * 
 * @author Mihael
 *
 */
public class SmartScriptLexer {

	/**
	 * Polje znakova koje predstavljaju ulaz i materijal za izradu tokena
	 */
	private char[] input;

	/**
	 * Referenca na sljedeci clan niza kojeg moramo provjeriti
	 */
	private int arrayIndex;

	/**
	 * Referenca na trenutno stanje lexera
	 */
	private LexerStates state;

	/**
	 * Javni konstruktor koji pretvara zadani String u polje,postavlja stanje u
	 * osnovno te index na 0
	 * 
	 * @param input
	 *            - ulaz u obliku Stringa kojeg zelimo "razbiti" na tokene
	 */
	public SmartScriptLexer(String input) {
		super();

		if (input == null || input.length() == 0) {
			throw new SmartScriptParserException("Duljina niza je 0 ili je sam argument null");
		}

		System.out.println("Input = " + input);

		this.input = cleanArray(input);

		arrayIndex = 0;
		state = LexerStates.NONTAG;
	}

	/**
	 * Metoda cijim se pozivanjem izraduje sljedeci token i vraca u obliku Stringa.
	 * U parseru se tada odlucuje sto ce ta vrijednost predstavljati,ovisno o nacinu
	 * rada
	 * 
	 * @return String kao token i vrijednost sljedeceg elementa
	 */
	public String getToken() {
		try {
			String pom = "";
			if (arrayIndex < input.length) {
				cleanSpaces();

				char inputChar = input[arrayIndex];

				if (state == LexerStates.FORLOOP || state == LexerStates.ECHO) {
					pom += functionWork();
				}

				else if (inputChar == '{' && input[arrayIndex + 1] == '$') {
					arrayIndex++;
					if (!checkEnd()) {
						pom += functionWork();
					} else {
						arrayIndex += 6;
						pom += "{$END$}";
					}
				} else {
					pom = textRead();
				}
			}

			if (pom.equals("")) {
				return null;
			} else {
				return pom;
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("POGRESKA!!");

			return null;
		}
	}

	/**
	 * Metoda koja cita znakove ako se lexer nalazi u stanju NONTAG
	 * {@link LexerStates}-a. Kada se dode do dijela koju nagovjesta promjenu
	 * stanja,metoda prekida citanje i vraca do sada procitani sadrzaj
	 * 
	 * @return
	 */
	private String textRead() {
		char inputChar = input[arrayIndex];
		StringBuilder builder = new StringBuilder().append(inputChar);
		arrayIndex++;

		while (arrayIndex < input.length) {
			inputChar = input[arrayIndex];

			if (inputChar == '\\') {
				builder.append(input[++arrayIndex]);
				System.out.println("Zapisujem: " + input[arrayIndex]);
				arrayIndex++;
			} else if (inputChar == '{' && input[arrayIndex + 1] == '$') {
				break;
			} else {
				if (inputChar != ' ')
					builder.append(inputChar);
				arrayIndex++;
			}
		}

		return builder.toString();
	}

	/**
	 * Metoda koja provjerava da li se doslo do podatka({$END$}) o zavrsteku
	 * nepraznog taga(npr for petlja)
	 * 
	 * @return true ako je doslo do kraja,inace false
	 */
	private boolean checkEnd() {
		// {$END$} -1 5
		String string = "";

		for (int i = -1; i <= 5; i++) {
			string += input[arrayIndex + i];
		}

		return string.equals("{$END$}");
	}

	/**
	 * Metoda koja racuna sljedeci token u slucaju da se lexer nalazi u stanju kada
	 * vrijede druga leksicka pravila
	 * 
	 * @return String kao sljedeci token
	 */
	private String functionWork() {
		// TODO Auto-generated method stub
		cleanSpaces();
		char inputChar = input[arrayIndex];
		StringBuilder pom = new StringBuilder();

		if (inputChar == '$' && state == LexerStates.NONTAG) {
			arrayIndex++;
			setState();
			cleanSpaces();
		}

		while ((inputChar = input[arrayIndex]) != '}') {

			if (inputChar == '$') {
				if (pom.toString().length() == 0) {
					arrayIndex += 2;
				}

				break;
			}

			else if (inputChar == ' ') {
				break;
			}

			else if (inputChar == '"') { // niz
				pom.append(inputChar);
				arrayIndex++;

				while ((inputChar = input[arrayIndex++]) != '"') {
					pom.append(inputChar);
				}

				pom.append(inputChar);
				break;
			}

			else {
				pom.append(inputChar);
				arrayIndex++;
			}
		}

		if (inputChar == ' ' || inputChar == '"' || inputChar == '$' && pom.toString().length() != 0) {
			return pom.toString().equals("") ? "" : pom.toString();
		} else if (inputChar == '$') {
			state = LexerStates.NONTAG;
			return "" + inputChar;
		} else {
			throw new SmartScriptParserException("Pogreske!");
		}

	}

	/**
	 * Metoda koja postavlja stanje lexera ovisno o izrazu koji se pojavi na
	 * ulazu(niz FOR aktivira nacin rada FORLOOP,a niz '=' aktivira echo nacin.
	 * Inace je nacin NONTAG
	 */
	private void setState() {
		// TODO Auto-generated method stub
		cleanSpaces();
		char inputChar;
		StringBuilder functionName = new StringBuilder();

		while ((inputChar = input[arrayIndex]) != ' ') {
			functionName.append(inputChar);
			arrayIndex++;
		}

		if (functionName.toString().toUpperCase().equals("FOR")) {
			state = LexerStates.FORLOOP;
		} else if (functionName.toString().equals("=")) {
			state = LexerStates.ECHO;
		} else {
			state = LexerStates.NONTAG;
		}
	}

	/**
	 * Metoda koja "brise"(preskace) nepotrebne praznine prije bitnih dijelova
	 */
	private void cleanSpaces() {
		// TODO Auto-generated method stub
		while (input[arrayIndex] == ' ') {
			arrayIndex++;
		}
	}

	/**
	 * Metoda vraca trenutno stanje u kojem se lexer nalazi(FOR,ECHO ili NONTAG)
	 * 
	 * @return {@link LexerStates}
	 */
	public LexerStates getState() {
		// TODO Auto-generated method stub
		return state;
	}

	/**
	 * Metoda koja provjerava nalazi li se u input leksicke zabrane,tj pojavljivanja
	 * znakova u rasporedu koji nije dozvoljen
	 * 
	 * @param string
	 *            - ulazni niz zadan preko argumenta konstruktora
	 * @return - "cisti niz"
	 * 
	 * @throws SmartScriptParserException
	 *             - ako se nadu znakovi u nedozvoljenom rasporedu
	 */
	private char[] cleanArray(String string) {
		StringBuilder builder = new StringBuilder();

		char[] array = string.toCharArray();
		boolean tags = false;

		for (int i = 0, length = string.length(); i < length; i++) {

			if (tags == false) {
				if (array[i] == '\\') {
					if (array[i + 1] == '\\' || array[i + 1] == '{') {
						i++;
					} else {
						throw new SmartScriptParserException("Pogreska kod dijela '\\' na indexu "+i);
					}
				} else if (array[i] == '{') {
					if (array[i + 1] == '$') {
						tags = true;
					} else {
						throw new SmartScriptParserException("Pogreska kod '{' na indexu "+i);
					}

				}

			} else {
				if (array[i] == '\\') {
					if (array[i + 1] == '\\' || array[i + 1] == '\"') {
						i++;
					} else {
						throw new SmartScriptParserException("Pogreska kod '\\' na indexu "+i);
					}
				} else if (array[i] == '}') {
					if (array[i - 1] == '$') {
						tags = false;
					}
					else {
						throw new SmartScriptParserException("Pogreska kod '}' na indexu "+i);
					}
				}

			}

			builder.append(array[i]);

		}

		return builder.toString().toCharArray();
	}

}
