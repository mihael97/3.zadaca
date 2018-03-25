package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.LexerStates;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * Javni razred koji implementira parser podataka. Pozivanjem lexera dolazimo do
 * dijelova sadrzaja niza i u koracima sastavljamo strukturirano stablo
 * 
 * @author Mihael
 *
 */
public class SmartScriptParser {
	/**
	 * Referenca na Lexer koji ce nam proizvesti tokene iz zadanog niza
	 */
	SmartScriptLexer lexer;

	/**
	 * Refernca na stog na kojem ce se stavljati cvorovi (tagovi)
	 */
	private ObjectStack stack;

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
		String token;
		stack = new ObjectStack();
		stack.push(new DocumentNode());

		try {
			while ((token = lexer.getToken()) != null) {
				if (token.equals("{$END$}")) {
					if (stack.size() == 0) { // prazan
						new SmartScriptParserException("Stog je prazan,zapis zapisi previse {$END$} zapisa!");
					} else {
						stack.pop();
					}
				} else if (lexer.getState() == LexerStates.FORLOOP) {
					ForLoopNode node = FORLoop(token);

					addNode(stack, node);

					stack.push(node);
				} else if (lexer.getState() == LexerStates.ECHO) {
					EchoNode node = Echo(token);

					addNode(stack, node);
				} else if (lexer.getState() == LexerStates.NONTAG) {
					TextNode node = new TextNode(token);

					addNode(stack, node);
				} else {
					System.out.println("Pogreska!");
				}
			}

			System.out.println("Stack velicina: " + stack.size());
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO: handle exception
		}
	}

	/**
	 * Metoda koja postavlja dijete zadnjem dodanom objektu na stogu
	 * 
	 * @param stack
	 *            - referenca na stog
	 * @param node
	 *            - cvor koji zelimo dodati kao dijete
	 */
	private void addNode(ObjectStack stack, Node node) {
		// TODO Auto-generated method stub
		Node fromStack = (Node) stack.pop();
		fromStack.addChildNode(node);
		stack.push(fromStack);
	}

	/**
	 * Metoda koja obraduje dobiveni token ako je on dosao dok je lexer bio u Echo
	 * nacinu rada
	 * 
	 * @param token
	 *            - string
	 * @return novi {@link EchoNode} sa svim dodanim {@link Element}
	 */
	private EchoNode Echo(String token) {
		ArrayIndexedCollection collection = new ArrayIndexedCollection(2);
		String element;

		collection.add(new ElementVariable(token));

		while ((element = lexer.getToken()) != null) {
			if (element.equals("$")) {
				break;
			}
			collection.add(makeElement(element));
		}

		Element[] elements = copyArray(collection.toArray(), collection.size());

		return new EchoNode(elements);
	}

	/**
	 * Metoda koja clanove polja objekata prebacuje u polje Elemenata zbog
	 * prihvatljivijeg formata
	 * 
	 * @param array
	 *            - polje objekata koje je bilo pohranjeno u
	 *            {@link ArrayIndexedCollection}
	 * @param size
	 *            - velicina objekata,kolicina
	 * @return - polje {@link Element} koji se salju u konstruktor
	 */
	private Element[] copyArray(Object[] array, int size) {
		Element[] forReturn = new Element[size];
		int index = 0;

		for (Object obj : array) {
			if (obj != null) {
				forReturn[index++] = (Element) obj;
			} else {
				break;
			}
		}

		return forReturn;
	}

	/**
	 * Metoda koja analizira podatke dobivene od lexera i u konacnici inicijalizra
	 * {@link ForLoopNode} koji ce biti dio stabla ForLoopNode moze imati 3 ili 4
	 * argumenta,inace se baca iznimka o prevelikom/premalom broju tokena iz lexera
	 * 
	 * @param string
	 *            - prvi token dobiven od lexera,a koji se odnosi na FOR
	 *            petlju(naziv varijable)
	 * @return {@link ForLoopNode} koji ce biti dio strukture
	 */
	private ForLoopNode FORLoop(String string) {
		// TODO Auto-generated method stub
		ElementVariable variable = new ElementVariable(checkName(string));
		Element startExpression = makeElement(lexer.getToken());
		Element endExpression = makeElement(lexer.getToken());
		Element stepExpression = null;

		if (!(string = lexer.getToken()).equals("$")) { // tri argumenta
			stepExpression = makeElement(string);
			string = lexer.getToken();

			if (!lexer.getState().equals(LexerStates.NONTAG)) {
				throw new SmartScriptParserException("Previse argumenata!");
			}
		}

		return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
	}

	/**
	 * Metoda provjerava zadovoljavaju li imena varijabli i funkcija odredene
	 * leksicke kriterije. Kriteriji: naziv mora zapoceti sa slovom,a kasnije se
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
		SmartScriptParserException exception = new SmartScriptParserException(
				"Ime varijable " + string + " se ne moze prihvatiti!");

		if (!Character.isLetter(nameArray[0])) {
			throw exception;
		} else {
			for (int i = 1; i < nameArray.length; i++) {
				char c = nameArray[i];
				if (!(Character.isLetter(c) || Character.isDigit(c) || c == '_')) {
					throw exception;
				}
			}
		}

		return string;
	}

	/**
	 * Metoda koja ovisno o dobivenom Stringu kao tokenu iz lexera stvara novi
	 * element koji ce poslije sastavljati cvorove
	 * 
	 * @param string
	 *            - String dobiven kao token od lexera
	 * @return eleemnt koji nasljeduje {@link Element} ovisno o predanom argumentu
	 */
	private Element makeElement(String string) {
		if (string == null) {
			throw new SmartScriptParserException("Premalo argumenata! Moraju biti 3 ili 4!");
		}

		if (string.equals("$")) { // kraj funkcije
			return null;
		}

		if (string.startsWith("@")) { // funkcija
			ElementFunction function = new ElementFunction(checkName(string.substring(1, string.length())));
			return function;
		}

		else if (isOperator(string)) { // operator
			ElementOperator operator = new ElementOperator(string);
			return operator;
		}

		else if (string.startsWith("\"")) { // string
			ElementString ele = makeString(string);
			if (ele != null) {
				return ele;
			}
		}

		else if (string.contains(".")) { // double
			ElementConstantDouble doubleElement = new ElementConstantDouble(Double.parseDouble(string));

			return doubleElement;
		}

		else if (Character.isLetter(string.charAt(0))) {
			ElementVariable variable = new ElementVariable(string);

			return variable;
		}

		else {

			ElementConstantInteger integerElement = new ElementConstantInteger(Integer.parseInt(string));

			return integerElement;
		}
		return null;

	}

	/**
	 * Metoda provjerava da li je zadani argument operator(+,-,/,^)
	 * 
	 * @param operator
	 *            - operator u obliku string koji zelimo provjeriti
	 * @return true ako je,inace false
	 */
	private boolean isOperator(String operator) {
		if (operator.equals("+") || operator.equals("-") || operator.equals("/") || operator.equals("^")
				|| operator.equals("*")) {
			return true;
		}

		return false;
	}

	/**
	 * Privatna ametoda koja stvara element u obliku Stringa za dodavanje u stablo
	 * 
	 * @param string
	 *            - vrijednost novog elementa
	 * @return ElementString
	 * 
	 * @throws SmartScriptParserException
	 *             - ako se String ne moze stvoriti
	 */
	private ElementString makeString(String string) {
		// TODO Auto-generated method stub
		if (string != null) {
			ElementString ele = new ElementString(string);
			System.out.println("Stvoren je " + ele.asText());
			return ele;
		}

		throw new SmartScriptParserException("Ne moze se stvoriti element String iz niza " + string);
	}

	/**
	 * Metoda koja vraca glavni cvor(Document Node) strukturiranog stabla. Ako
	 * stablo jos nije izradeno,tj nema dodanih cvorova,metoda vraca
	 * {@link SmartScriptParserException}
	 * 
	 * @return {@link DocumentNode} ako on postoji,inace iznimka
	 * 
	 * @throws SmartScriptParserException
	 */
	public DocumentNode getDocumentNode() {
		if (stack == null) {
			throw new SmartScriptParserException();
		} else {
			Node node = null;

			while (stack.size() != 0) {
				node = (Node) stack.pop();
			}

			return (DocumentNode) node;
		}
	}
}
