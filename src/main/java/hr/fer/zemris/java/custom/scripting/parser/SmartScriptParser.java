package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;

/**
 * Javni razred koji implementira parser podataka. Pozivanjem lexera dolazimo do
 * dijelova sadrzaja niza i u koracima sastavljamo strukturirano stablo
 * 
 * @author Mihael
 *
 */
public class SmartScriptParser {
	/**
	 * String kojim inicijaliziramo Lexer
	 */
	private String input;
	/**
	 * Referenca na Lexer koji ce nam proizvesti tokene iz zadanog niza
	 */
	SmartScriptLexer lexer;

	/**
	 * Javni konstruktor koji inicijalizira Lexer s nizom kojeg zelimo parsirati
	 * 
	 * @param input
	 *            - niz koji zelimo parsirati
	 * @throws NullPointerException
	 *             - ako je ulaz null
	 */
	public SmartScriptParser(String input) {
		if (input == null) {
			throw new NullPointerException("Naziv je null!");
		}
		this.input = input;
		lexer = new SmartScriptLexer(input);
		getTokens();
	}

	/**
	 * Metoda koja poziva proizvodnju tokena od Lexera sve dok ne primi null
	 * vrijednost(kraj). S obzirom na pocetak dobivenog tokena,metoda poziva
	 * specificirane metode za izradu elemenata
	 */
	private void getTokens() {
		// TODO Auto-generated method stub
		String pomString = "";
		while ((pomString = lexer.getToken()) != null) {
			if (pomString.equals("$")) { // zapocinje fja
				function();
			} else if (pomString.startsWith("\"")) { // navodnici
				makeString(pomString);
			}
		}
	}

	/**
	 * Javn ametoda koja stvara element u obliku Stringa za dodavanje u stablo
	 * 
	 * @param string
	 *            - vrijednost novog elementa
	 */
	private void makeString(String string) {
		// TODO Auto-generated method stub
		if (string != null) {
			ElementString ele = new ElementString(string);
			System.out.println("napravljen string: " + ele.asText());
		}
	}

	/**
	 * Funckcija koja se bavi razradom izraza ako se radi o funkciji(mogucnosti su
	 * for petlaj ili echo
	 * 
	 * @throws SmartScriptParserException
	 *             - ako ime "taga" nije dozvoljeno
	 */
	private void function() {
		// TODO Auto-generated method stub
		String pomString = "";

		if ((pomString = lexer.getToken()).equals("FOR")) {
			FOREcho();
		} else if (pomString.equals("=")) { // echo
			FOREcho();
		} else {
			throw new SmartScriptParserException("Pocetak nije dobar!");
		}
	}

	/**
	 * Metoda koja razraduje izraz funkcije i stavara nove elemente stavla ovisno o
	 * prozivedenom tokenu lexera Upute: - izraz moze dobiti 3 ili 4 argumenta,inace
	 * se baca iznimka
	 * 
	 * @throws IllegalArgumentException
	 *             - ako se u izrazu predaju vise od 4 argumenta
	 */
	private void FOREcho() {
		// TODO Auto-generated method stub
		int counter = 1;
		ElementVariable name = new ElementVariable(checkName(lexer.getToken()));
		System.out.println("Napravljena je varijabla: " + name.asText());

		while ((input = lexer.getToken()) != null) {
			if (input.equals("$")) { // kraj funkcije
				return;// izlazimo van jer je obrada for-a gotova,znam da nije lijep izlazak,ali nismo
						// u petlji da bismo koristili break
			} else if (++counter > 4) {
				throw new IllegalArgumentException();
			}

			if (input.startsWith("@")) { // funkcija
				ElementFunction function = new ElementFunction(checkName(input.substring(1, input.length())));
				System.out.println("Funkcija " + function.asText());
			}

			else if (isOperator(input)) { // operator
				ElementOperator operator = new ElementOperator(input);
				System.out.println("Stvoren novi operator " + operator.asText());
			}

			else if (input.startsWith("\"")) { // string
				makeString(input);
			}

			else if (input.contains(".")) { // double
				ElementConstantDouble doubleElement = new ElementConstantDouble(Double.parseDouble(input));
				System.out.println("Napravljen double " + doubleElement.asText());
			}

			else {
				ElementConstantInteger integerElement = new ElementConstantInteger(Integer.parseInt(input));
				System.out.println("Napravljen integer " + integerElement.asText());
			}
		}

	}

	/**
	 * Metoda provjerava da li je zadani argument operator(+,-,/,^)
	 * 
	 * @param operator
	 *            - operator u obliku string koji zelimo provjeriti
	 * @return true ako je,inace false
	 */
	private boolean isOperator(String operator) {
		if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("^")) {
			return true;
		}

		return false;
	}

	/**
	 * Metoda provjerava zadovoljavaju li imena varijabli i funkcija odredene
	 * leksicke kriterije Kriteriji: - naziv mora zapoceti sa slovom,a kasnije se
	 * mogu pojavljivati slova,brojevi i donje povlake("underscore") u neogranicenom
	 * broju
	 * 
	 * @param string
	 *            - niz ciju validnost zelimo provjeriri
	 * @return - predani argument ako je zadovoljava uvjete inace iznimka
	 * 
	 * @throws SmartScriptParserException
	 *             - ako niz ne zadovoljava kriterije
	 */
	private String checkName(String string) {
		char[] nameArray = string.toCharArray();

		if (!Character.isLetter(nameArray[0])) {
			throw new SmartScriptParserException();
		} else {
			for (int i = 1; i < nameArray.length; i++) {
				char c = nameArray[i];
				if (!(Character.isLetter(c) || Character.isDigit(c) || c == '_')) {
					throw new SmartScriptParserException();
				}
			}
		}

		return string;
	}

}
