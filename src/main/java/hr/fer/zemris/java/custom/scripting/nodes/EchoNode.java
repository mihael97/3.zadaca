package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Razred koji implementira naredbu koja stvara neki izlaz. Nasljeduje klasu
 * Node. Sadrzi polje Elementa
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
		
		System.out.println("Ispis Echo noda");
		
		for(Element ele:elements) {
			System.out.println(ele.asText());
		}
	}

	/**
	 * Metoda koja vraca sve elemente u obliku polje Elemenata
	 * 
	 * @return Element[]
	 */
	public Element[] getElements() {
		return elements;
	}
}
