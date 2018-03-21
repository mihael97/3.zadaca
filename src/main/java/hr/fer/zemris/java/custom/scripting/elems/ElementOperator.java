package hr.fer.zemris.java.custom.scripting.elems;

public class ElementOperator extends Element {
	private String symbol;

	public  ElementOperator(String value) {
		// TODO Auto-generated constructor stub
		if (value == null) {
			throw new NullPointerException("Vrijednost je null!");
		}
		this.symbol = value;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return symbol;
	}
}
