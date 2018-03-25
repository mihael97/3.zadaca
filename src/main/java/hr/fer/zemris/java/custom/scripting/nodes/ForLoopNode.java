package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Razred koji predstavlja for petlju i nasljeduje klasu Node. Varijable su
 * naziv varijable,pocetni izraz,zavrsni izraz i izraz koji predstavlja korak.
 * Prve tri vrijednosti ne mogu biti null,dok cetvrta moze
 * 
 * @author Mihael
 *
 */
public class ForLoopNode extends Node {
	/**
	 * Vrijednost
	 */
	private ElementVariable variable;
	/**
	 * Pocetni izraz
	 */
	private Element startExpression;
	/**
	 * Zavrsni izraz
	 */
	private Element endExpression;
	/**
	 * 'Preskakuci izraz'
	 */
	private Element stepExpression;

	/**
	 * Javni konstruktor koji inicijalizira sve varijable
	 * 
	 * @param variable
	 *            - naziv varijable
	 * @param startExpression
	 *            - pocetni izraz
	 * @param endExpression
	 *            - zavrsni izraz
	 * @param stepExpression
	 *            - izraz koji predstavlja korak
	 * 
	 * @throws NullPointerException
	 *             - ako je jedan od prva tri izraza null
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		if (variable == null || startExpression == null || endExpression == null) {
			throw new NullPointerException("Jedan od predanih argumenata je null!");
		}

		System.out.println("Ispis FOR loopa: ");
		this.variable = variable;
		System.out.println("Naziv: " + variable.asText());
		this.startExpression = startExpression;
		System.out.println("Pocetni :" + startExpression.asText());
		this.endExpression = endExpression;
		System.out.println("Zavrsni: " + endExpression.asText());
		this.stepExpression = stepExpression;

		if (stepExpression != null) {
			System.out.println("Korak: " + stepExpression.asText());
		}
	}

	/**
	 * Metoda koja vraca naziv varijable
	 * 
	 * @return {@link ElementVariable}
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Metoda koja vraca pocetni izraz
	 * 
	 * @return {@link Element}
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Metoda koja vraca zavrsni izraz
	 * 
	 * @return {@link Element}
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Metoda koja vraca izraz koji predstavlja korak
	 * 
	 * @return {@link Element}
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}
