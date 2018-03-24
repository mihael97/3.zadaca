package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Razred koji predstavlja for petlju i nasljeduje klasu Node
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
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		super();
		if (variable == null || startExpression == null || endExpression == null) {
			throw new NullPointerException("Jedan od predanih argumenata je null!");
		}

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Metoda koja vraca vrijednost varijable
	 * 
	 * @return {@link ElementVariable}
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Metoda koja vraca pocetni izraz
	 * 
	 * @return Element
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Metoda koja vraca zavrsni izraz
	 * 
	 * @return Element
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Metoda koja vraca izraz
	 * 
	 * @return
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

}
