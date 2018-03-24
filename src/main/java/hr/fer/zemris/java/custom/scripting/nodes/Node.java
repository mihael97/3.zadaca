package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Razred koji implementira clana strukture
 * 
 * @author ime
 *
 */
public class Node {
	/**
	 * Referenca na kolekciju za pohranu djece
	 */
	private ArrayIndexedCollection collection = null;

	/**
	 * Metoda koja dodaje dijete u kolekciju(polje)
	 * 
	 * @param child
	 *            - dijete koje zelimo dodati
	 */
	public void addChildNode(Node child) {
		if (collection == null) {
			collection = new ArrayIndexedCollection(2);
		}

		collection.add(child);
	}

	/**
	 * Metoda koja vraca broj djece koji se nalazi u stablu
	 * 
	 * @return int - broj djece
	 */
	public int numberOfChildren() {
		return collection.size();
	}

	/**
	 * Metoda koja vraca clana na odredenom indexu,odnosno iznimku
	 * 
	 * @param index
	 * @return Node
	 * @throws IndexOutOfBoundsException
	 *             - ako index nije u rasponu
	 */
	public Node getChild(int index) {
		return (Node) collection.get(index);
	}
}
