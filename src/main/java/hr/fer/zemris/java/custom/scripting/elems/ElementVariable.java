package hr.fer.zemris.java.custom.scripting.elems;

public class ElementVariable extends Element {
	private String name;

	public ElementVariable(String name) {
		// TODO Auto-generated constructor stub
		if (name == null) {
			throw new NullPointerException("Ime ne smije biti null");
		} else {
			this.name = name;
		}
	}

	public String getName() {
		return name;
	}

	@Override
	public String asText() {
		// TODO Auto-generated method stub
		return name;
	}
	
	
}
