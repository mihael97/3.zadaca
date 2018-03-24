package hr.fer.zemris.java.custom.scripting.lexer;

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
		if (input == null) {
			throw new NullPointerException("Ulaz je null!");
		}
		System.out.println("Kao argument dobio sam " + input);
		this.input = input.toCharArray();
		arrayIndex = 0;
		state = LexerStates.NONFUNCTION;
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
			cleanSpaces();
			char inputChar = input[arrayIndex];

			if (state == LexerStates.FUNCTION) {
				pom = functionWork();
			} else if (inputChar == '"') {
				pom += input[arrayIndex++];
				while (true) {
					inputChar = input[arrayIndex];

					if (inputChar == '\\' && input[arrayIndex + 1] == '"') {
						pom += input[arrayIndex + 1];
						arrayIndex += 2;
					} else if (inputChar == '"') {
						pom += inputChar;
						arrayIndex++;
						break;
					} else {
						pom += inputChar;
						arrayIndex++;
					}
				}

				pom += input[arrayIndex++];
			} else if (inputChar == '{') {
				arrayIndex++;
				state = LexerStates.FUNCTION;
				pom = functionWork();
			} else {
				return null;
			}

			return pom;
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
			return null;
		}
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
		char inputChar;
		String pom = "";

		while ((inputChar = input[arrayIndex]) != '}') {

			if (inputChar == '$') {
				pom += inputChar;
				arrayIndex++;
				break; // javljamo pocetak fje
			} else if (inputChar == ' ') {
				break;
			} else if (inputChar == '"') { // niz
				pom += inputChar;
				arrayIndex++;
				while ((inputChar = input[arrayIndex]) != '"') {
					pom += inputChar;
					arrayIndex++;
				}

				pom += inputChar; // dodajemo "
				arrayIndex++;
				break;
			} else {
				pom += inputChar;
				arrayIndex++;
			}
		}

		if (inputChar == ' ' || inputChar == '"' || inputChar == '$') {
			return pom.equals("") ? null : pom;
		} else {
			state = LexerStates.NONFUNCTION;
			return null;
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

}
