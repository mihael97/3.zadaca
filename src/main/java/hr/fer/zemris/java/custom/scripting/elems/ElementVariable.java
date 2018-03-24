package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji predstavlja varijablu sa svojim nazivom
 * 
 * @author Mihael
 *
 */
public class ElementVariable extends Element {
	/**
	 * Naziv varijable u obliku String
	 */
	private String name;

	/**
	 * Konstruktor koji inicijalizira vrijednost imena varijable
	 * 
	 * @param name
	 *            - ime varijable
	 * @throws NullPointerException
	 *             - ako je naziv null
	 */
	public ElementVariable(String name) {
		// TODO Auto-generated constructor stub
		if (name == null) {
			throw new NullPointerException("Ime ne smije biti null");
		} else {
			this.name = name;
		}
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return name;
	}

}
