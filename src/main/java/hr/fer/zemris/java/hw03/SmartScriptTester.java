//package hr.fer.zemris.java.hw03;
//
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
//import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
//import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
//
//public class SmartScriptTester {
//	public static void main(String[] args) {
//		String filepath;
//		
//		if(args.length!=1) {
//			throw new IllegalArgumentException();
//		} else {
//			filepath=args[0];
//		}
//		
//		String docBody;
//		docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
//
//		SmartScriptParser parser = new SmartScriptParser(docBody);
//		DocumentNode document = parser.getDocumentNode();
//		String originalDocumentBody = createOriginalDocumentBody(document);
//		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
//		DocumentNode document2 = parser2.getDocumentNode();
//		// now document and document2 should be structurally identical trees
//	}
//}
