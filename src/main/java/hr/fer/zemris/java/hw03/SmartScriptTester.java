package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * 
 * Glavni program u kojem provjeramo funkcionalnost programa
 * 
 * @author Mihael
 *
 */
public class SmartScriptTester {
	/**
	 * Glavna metoda gdje ispitujemo funkcionalnost parsera i leksera
	 * 
	 * @param args
	 *            - ne koristi se
	 */
	public static void main(String[] args) {
		String filepath = "src\\test\\resources\\dokument1.txt";

		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();

		if (originalDocumentBody.equals(createOriginalDocumentBody(document2))) {
			System.out.println("Isti su!");
		} else {
			System.out.println("Pogreska!");
		}
	}

	/**
	 * Metoda koja rekonstruira tekst iz {@link DocumentNode} i vraca ga u obliku
	 * teksta
	 * 
	 * @param document
	 *            - referenca na {@link DocumentNode} koji je prvi stavljen na stog
	 * 
	 * @return prikaz teksta u obliku Stringa
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		try {
			StringBuilder builder = new StringBuilder();
			int numberOfChildren = document.numberOfChildren();

			for (int i = 0; i < numberOfChildren; i++) {
				if (document.getChild(i) instanceof ForLoopNode) {
					builder.append(printForLoop((ForLoopNode) document.getChild(i)));
				} else if (document.getChild(i) instanceof TextNode) {
					builder.append(((TextNode) document.getChild(i)).getText());
				} else if (document.getChild(i) instanceof EchoNode) {
					builder.append(((EchoNode) document.getChild(i)).toString());
				}
			}

			return builder.toString();
		} catch (ArrayIndexOutOfBoundsException e) {
			// prosli smo svu djecu
			return null;
		}
	}

	/**
	 * Metoda koja ispisuje ForPetlju i sve cvorove ugnjezdene u nju
	 * 
	 * @param child
	 *            - referenca na {@link ForLoopNode}
	 * 
	 * @return prikaz cvora i svih ovisnih cvorova u obliku petlje
	 */
	private static String printForLoop(ForLoopNode child) {
		StringBuilder builder = new StringBuilder().append(child.toString());
		int numberOfChildren = child.numberOfChildren();

		for (int i = 0; i < numberOfChildren; i++) {
			if (child.getChild(i) instanceof ForLoopNode) {
				builder.append(printForLoop((ForLoopNode) child.getChild(i)));
			} else if (child.getChild(i) instanceof TextNode) {
				builder.append(((TextNode) child.getChild(i)).getText());
			} else if (child.getChild(i) instanceof EchoNode) {
				builder.append(((EchoNode) child.getChild(i)).toString());
			}
		}

		builder.append("{$END$}");
		return builder.toString();
	}
}
