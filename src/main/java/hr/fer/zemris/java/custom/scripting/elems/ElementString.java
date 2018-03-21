package hr.fer.zemris.java.custom.scripting.elems;

public class ElementString extends Element {
	private String value;

	public ElementString(String value) {
		// TODO Auto-generated constructor stub
		if (value == null) {
			throw new NullPointerException("Vrijednost je null!");
		}
		this.value = value;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return value;
	}
	
	
}
