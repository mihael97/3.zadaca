package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji implementira funkciju s varijablom za naziv
 * 
 * @author Mihael
 *
 */
public class ElementFunction extends Element {
	/**
	 * Naziv funkcije
	 */
	private String name;

	/**
	 * Konstruktor koji inicijalizira naziv funkcije
	 * 
	 * @param value
	 *            - naziv funkcije
	 * @throws NullPointerException
	 *             - ako je naziv null
	 */
	public ElementFunction(String value) {
		// TODO Auto-generated constructor stub
		if (value == null) {
			throw new NullPointerException("Vrijednost je null!");
		}
		this.name = value;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return name;
	}
}
