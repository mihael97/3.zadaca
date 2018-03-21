package hr.fer.zemris.java.custom.scripting.elems;

public class ElementFunction extends Element {
	private String name;

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
