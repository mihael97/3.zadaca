package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Razred koji implementira naredbu koja stvara neki izlaz. Nasljeduje klasu
 * Node
 * 
 * @author Mihael
 *
 */
public class EchoNode extends Node {
	/**
	 * Elementi
	 */
	Element[] elements;

	/**
	 * Javni konstruktor koji prima referencu na elemente i inicijalizira ih
	 * 
	 * @param elements
	 *            - elementi
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = Arrays.copyOf(elements, elements.length);
	}

	/**
	 * Metoda koja vraca sve elemente u obliku polje Elemenata
	 * 
	 * @return element[]
	 */
	public Element[] getElements() {
		return elements;
	}
}
