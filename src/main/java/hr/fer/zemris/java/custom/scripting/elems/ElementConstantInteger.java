package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantInteger extends Element {
	private int value;

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
