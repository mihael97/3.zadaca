package hr.fer.zemris.java.custom.scripting.lexer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

@SuppressWarnings("javadoc")
public class Proba {
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		String docBody;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(args[0])), StandardCharsets.UTF_8);
			SmartScriptParser lexer = new SmartScriptParser(docBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Pogreska u probi!");
		}
	}
}
