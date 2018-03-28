package hr.fer.zemris.java.hw03.prob1;

/**
 * Javni razred koji implementira lexer
 * 
 * @author Mihael
 *
 */
public class Lexer {
	/**
	 * Ulazni niz
	 */
	private char[] data;
	/**
	 * Zadnji token
	 */
	private Token token;
	/**
	 * Trenutni index
	 */
	private int currentIndex;

	/**
	 * Brojac znakova
	 */
	private int counter = 0;

	/**
	 * Reference na trenutno stanje sustava
	 */
	private LexerState state;

	/**
	 * Javni konstruktor koji stvara novi Lexer
	 * 
	 * @param text
	 *            - referenca na ulazni niz
	 * @throws IllegalArgumentException
	 *             - ako je tekst null
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Tekst ne smije biti null!");
		}

		if (text.trim().equals("")) {
			data = new char[0];
		} else if (text.trim().equals("\\")) {
			token = new Token(TokenType.EOF, null);
		} else {
			getCharacters(text);
		}

		state = LexerState.BASIC;
	}

	/**
	 * Metoda koja puni polje znakova sa sadrzajem iz ulaznog zapisa
	 * 
	 * @param text
	 *            - ulazni izraz
	 */
	private void getCharacters(String text) {
		data = new char[text.length()];

		for (int i = 0, pomIndex = 0; i < text.length(); i++) {
			Character c = text.charAt(i);

			if (!checkToken(c)) {
				data[pomIndex++] = c;
				counter++;
			} else if (c == ' ' && (pomIndex != 0
					&& (Character.isLetter(data[pomIndex - 1]) || Character.isDigit(data[pomIndex - 1])))) {
				data[pomIndex++] = ' ';
			}
		}

		currentIndex = 0;
	}

	/**
	 * Metoda koja provjerava je li zadani argument simbol
	 * 
	 * @param c
	 *            - znak kojeg zelimo provjeriti
	 * @return true ako je,inace false
	 */
	private boolean isSymbol(char c) {
		if (c == '.' || c == '-' || c == '!' || c == '?' || c == '#' || c == ';') {
			return true;
		}

		return false;
	}

	/**
	 * Metoda koja vraca sljedeci token koji je grupiran po pravilima ovisno u kojem
	 * se rezimu rada nalazi lexer
	 * 
	 * @return sljedeci {@link Token}
	 * 
	 * @throws LexerException
	 *             - ako dode do pogreske(previse puta pozvan nextToken,\ se nalazi
	 *             ispred slova ili je jedini clan niza)
	 */
	public Token nextToken() {
		Token previousToken = token;

		if (token != null && token.type == TokenType.EOF) {
			throw new LexerException("Previse puta pozvan nextToken!");
		}

		if (data.length != 0) {
			if (data[currentIndex] == '\\') {
				if (Character.isLetter(data[currentIndex + 1])) {
					throw new LexerException("\\ ne smije biti ispred slova!");
				} else if (counter == 0) {
					throw new LexerException("\\ je sam!");
				}
			} else if (data[currentIndex] == ' ') {
				currentIndex++;
			} else if (state == LexerState.EXTENDED) {
				StringBuilder pom = new StringBuilder();

				while (data[currentIndex] != ' ' && data[currentIndex] != '#') {
					pom.append(data[currentIndex++]);
				}

				if (data[currentIndex] != '#') {
					currentIndex++;
					return new Token(TokenType.WORD, pom.toString());
				} else if (pom.length() != 0) {
					return new Token(TokenType.WORD, pom.toString()); // saljemo ostatak ako postoji
				}
			}

			Character c = null;
			if ((c = checkSymbol(currentIndex)) != null) {
				currentIndex++;
				token = new Token(TokenType.SYMBOL, c);
			}

			else if ((c = checkLetter(currentIndex)) != null) {

				StringBuilder pom = new StringBuilder().append(c);
				while ((c = checkLetter(currentIndex)) != null) {

					pom.append(c);
				}

				token = new Token(TokenType.WORD, pom.toString());
			}

			else if (Character.isDigit(data[currentIndex])) {
				StringBuilder pom = new StringBuilder().append(data[currentIndex++]);

				while (Character.isDigit(data[currentIndex])) {
					pom.append(data[currentIndex++]);
				}

				try {
					token = new Token(TokenType.NUMBER, Long.parseLong(pom.toString()));

				} catch (NumberFormatException e) {
					// TODO: handle exception
					throw new LexerException("Preveliki broj!");
				}

			}
		}

		return previousToken == token ? (token = new Token(TokenType.EOF, null)) : token;

	}

	/**
	 * Provjerava da li se broj moze zapisati kao String(ispred njega mora biti \\)
	 * 
	 * @param c
	 *            - znak
	 * 
	 * @return true ako se moze,inace false
	 */
	private boolean checkDigit(char c) {
		// TODO Auto-generated method stub
		if (data[currentIndex] == '\\' && Character.isDigit(data[currentIndex + 1])) {
			return true;
		}
		return false;
	}

	/**
	 * Metoda provjera pozivanjem metoda isSymbol da li je znaka na poziciji zadanoj
	 * preko argumenata simbok
	 * 
	 * @param index
	 *            - pozicija
	 * @return null ako nije,inace znak
	 */
	private Character checkSymbol(int index) {
		return isSymbol(data[index]) ? data[index] : null;
	}

	/**
	 * Metoda provjerava treba li znak staviti u token
	 * 
	 * @param index
	 *            - pozicija
	 * @return null ako ne treba,inace znak
	 */
	private Character checkLetter(int index) {
		if (Character.isLetter(data[index])) {
			currentIndex++;
			return data[index];
		} else if (checkDigit(data[index])) {
			currentIndex += 2;
			return data[++index];
		} else if (data[index] == '\\' && data[index + 1] == '\\') {
			currentIndex += 2;
			return data[++index];
		}

		// currentIndex++;
		return null;
	}

	/**
	 * Metoda provjerava treba li znak dodati u niz(praznine se ne dodaju u niz,osim
	 * ako nije razmak izmedu dva tokena)
	 * 
	 * @param c
	 *            - znak kojeg provjeravamo
	 * @return true ako ga ne trebamo,inace false
	 */
	private boolean checkToken(char c) {
		// TODO Auto-generated method stub
		if (c == ' ' || c == '\r' || c == '\n' || c == '\t' || c == ' ') {
			return true;
		}

		return false;
	}

	/**
	 * Metoda koja vraca zadnje generirani token
	 * 
	 * @return {@link Token}
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Metoda koja postavlja stanje rada
	 *
	 * @param state
	 *            - zeljeno stanje rada
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Stanje ne moze biti null!");
		}
		this.state = state;
	}

}
