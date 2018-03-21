package hr.fer.zemris.java.custom.scripting.elems;

public class ElementConstantDouble extends Element {
	private double value;

	public ElementConstantDouble(double value) {
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return Double.valueOf(value).toString();
	}
}
