package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Razred koji implementira tekstualni podatak i nasljeduje klasu Node
 * 
 * @author Mihael
 *
 */
public class TextNode {
	/**
	 * Privatna varijabla koja sprema tekst
	 */
	private String text;

	/**
	 * Javni konstruktor koji inicijalizira tekst
	 * 
	 * @param text
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Metoda koja vraca vrijednost teksta
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}

}
