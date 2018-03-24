package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji implementira cijelobrojnu vrijednost
 * 
 * @author Mihael
 *
 */
public class ElementConstantInteger extends Element {
	/**
	 * Vrijednost u obliku cijelog broja
	 */
	private int value;

	/**
	 * Javni konstruktor koji inicijalizira vrijednost cijelog broja
	 * 
	 * @param value
	 *            - vrijednost
	 */
	public ElementConstantInteger(int value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return Integer.valueOf(value).toString();
	}

}
