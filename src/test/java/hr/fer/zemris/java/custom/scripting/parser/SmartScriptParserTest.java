package hr.fer.zemris.java.custom.scripting.parser;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.TypeToken;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.hw03.SmartScriptTester;

@SuppressWarnings("javadoc")
public class SmartScriptParserTest {
	
	
	@Test
	public void firstTest() {
		String document = loader("src/test/resources/doc1.txt");
		SmartScriptLexer lexer = new SmartScriptLexer(document);

		assertEquals("This is sample text.\r\n", lexer.getToken().toString());
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("FOR", lexer.getToken().toString());
		assertEquals("i", lexer.getToken().toString());
		assertEquals(1, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(10, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(1, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		
		assertEquals("\r\nThis is ", lexer.getToken().toString());
		
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("=", lexer.getToken().toString());
		assertEquals("i", lexer.getToken().toString());
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		
		assertEquals("-th time this message is generated.\r\n" + "", lexer.getToken().toString());
		
		assertEquals(TypeToken.END, lexer.getToken().getType());
		
		assertEquals("\r\n", lexer.getToken().toString());
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("FOR", lexer.getToken().toString());
		assertEquals("i", lexer.getToken().toString());
		assertEquals(0, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(10, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(2, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		
		assertEquals("\r\n" + "sin(", lexer.getToken().toString());
		
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("=", lexer.getToken().toString());
		assertEquals("i", lexer.getToken().toString());
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		
		assertEquals("^2) = ", lexer.getToken().toString());
		
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("=", lexer.getToken().toString());
		assertEquals("i", lexer.getToken().toString());
		assertEquals("i", lexer.getToken().toString());
		assertEquals("*", lexer.getToken().toString());
		assertEquals("sin", lexer.getToken().toString());
		assertEquals("\"0.000\"", lexer.getToken().toString());
		assertEquals("decfmt", lexer.getToken().toString());
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		
		assertEquals("\r\n",lexer.getToken().toString());
		
		assertEquals(TypeToken.END, lexer.getToken().getType());
		assertNull(lexer.getToken());
	}
	
	@Test(expected=LexerException.class)
	public void exception1() {
		String document = loader("src/test/resources/doc2.txt");
		@SuppressWarnings("unused")
		SmartScriptLexer lexer = new SmartScriptLexer(document);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=SmartScriptParserException.class)
	public void invalidVariableName() {
		String document= loader("src/test/resources/doc3.txt");
		SmartScriptParser parser=new SmartScriptParser(document);
	}
	
	@Test
	public void FORLoop3ArgumentsTest() {
		String document= loader("src/test/resources/doc4.txt");
		SmartScriptLexer lexer=new SmartScriptLexer(document);
		
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("FOR", lexer.getToken().toString());
		assertEquals("a", lexer.getToken().toString());
		assertEquals(9, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(4, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		
		assertEquals(TypeToken.END, lexer.getToken().getType());
		assertNull(lexer.getToken());
	}
	
	@Test
	public void FORLoop4ArgumentsTest() {
		String document= loader("src/test/resources/doc6.txt");
		SmartScriptLexer lexer=new SmartScriptLexer(document);
		
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("FOR", lexer.getToken().toString());
		assertEquals("a", lexer.getToken().toString());
		assertEquals(9, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(4, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(8, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		
		assertEquals(TypeToken.END, lexer.getToken().getType());
		assertNull(lexer.getToken());
	}

	@SuppressWarnings("unused")
	@Test(expected=SmartScriptParserException.class)
	public void FORLoopManyArguments() {
		String document= loader("src/test/resources/doc9.txt");
		SmartScriptParser parser=new SmartScriptParser(document);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=SmartScriptParserException.class)
	public void FORLoopNotEnoughArguments() {
		String document= loader("src/test/resources/doc7.txt");
		SmartScriptParser parser=new SmartScriptParser(document);
	}
	
	@SuppressWarnings("unused")
	@Test(expected=SmartScriptParserException.class)
	public void FORLoopFunction() {
		String document= loader("src/test/resources/doc8.txt");
		SmartScriptParser parser=new SmartScriptParser(document);
	}
	
	@Test
	public void EchoLoop() {
		String document= loader("src/test/resources/doc10.txt");
		SmartScriptLexer lexer=new SmartScriptLexer(document);
		
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("=", lexer.getToken().toString());
		assertEquals("\"Mihael\"", lexer.getToken().toString());
		assertEquals("\"Macuka\"", lexer.getToken().toString());
		assertEquals(8, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(9, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(1, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(0, Integer.parseInt(lexer.getToken().toString()));
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		
		assertEquals(TypeToken.END, lexer.getToken().getType());
		assertNull(lexer.getToken());;
	}
	
	@Test (expected=LexerException.class)
	public void EchoLoopException() {
		String document= loader("src/test/resources/doc11.txt");
		@SuppressWarnings("unused")
		SmartScriptLexer lexer=new SmartScriptLexer(document);
	}
	
	@Test
	public void someText() {
		String document= loader("src/test/resources/doc12.txt");
		SmartScriptLexer lexer=new SmartScriptLexer(document);
	
		assertEquals("A tag follows ", lexer.getToken().toString());
		assertEquals(TypeToken.TAGSTART, lexer.getToken().getType());
		lexer.setState();
		assertEquals("=", lexer.getToken().toString());
		assertEquals("\"Joe \"Long\" Smith\"", lexer.getToken().toString());
		assertEquals(TypeToken.TAGEND, lexer.getToken().getType());
		lexer.setState();
		assertNull(lexer.getToken());
	}
	
	@Test(expected=SmartScriptParserException.class)
	public void unclosedFORLoop() {
		String document= loader("src/test/resources/doc13.txt");
		@SuppressWarnings("unused")
		SmartScriptParser lexer=new SmartScriptParser(document);
	}
	
	@Test
	public void structureTest1 () {
		SmartScriptParser parser=new SmartScriptParser(loader("src/test/resources/dokument2.txt"));
		DocumentNode document1=parser.getDocumentNode();
		String string=SmartScriptTester.createOriginalDocumentBody(document1);
		SmartScriptParser parser2=new SmartScriptParser(string);
		DocumentNode document2=parser2.getDocumentNode();
		
		assertEquals(4, document1.numberOfChildren());
		assertEquals(4, document2.numberOfChildren());
		
		assertEquals(true,string.equals(SmartScriptTester.createOriginalDocumentBody(document2)));
	}
	
	
	@Test
	public void structureTest2 () {
		SmartScriptParser parser=new SmartScriptParser(loader("src/test/resources/dokument3.txt"));
		DocumentNode document1=parser.getDocumentNode();
		String string=SmartScriptTester.createOriginalDocumentBody(document1);
		SmartScriptParser parser2=new SmartScriptParser(string);
		DocumentNode document2=parser2.getDocumentNode();
		
		assertEquals(2, document1.numberOfChildren());
		assertEquals(2, document2.numberOfChildren());
		
		assertEquals(true,string.equals(SmartScriptTester.createOriginalDocumentBody(document2)));
	}
	
	@Test
	public void structureTest3 () {
		SmartScriptParser parser=new SmartScriptParser(loader("src/test/resources/dokument4.txt"));
		DocumentNode document1=parser.getDocumentNode();
		String string=SmartScriptTester.createOriginalDocumentBody(document1);
		SmartScriptParser parser2=new SmartScriptParser(string);
		DocumentNode document2=parser2.getDocumentNode();
		
		assertEquals(3, document1.numberOfChildren());
		assertEquals(3, document2.numberOfChildren());
		assertEquals(5, document1.getChild(1).numberOfChildren());
		
		assertEquals(true,string.equals(SmartScriptTester.createOriginalDocumentBody(document2)));
	}
	
	@Test
	public void structureTest4 () {
		SmartScriptParser parser=new SmartScriptParser(loader("src/test/resources/dokument5.txt"));
		DocumentNode document1=parser.getDocumentNode();
		String string=SmartScriptTester.createOriginalDocumentBody(document1);
		SmartScriptParser parser2=new SmartScriptParser(string);
		DocumentNode document2=parser2.getDocumentNode();
		
		assertEquals(4, document1.numberOfChildren());
		assertEquals(4, document2.numberOfChildren());
		
		assertEquals(0, document1.getChild(1).numberOfChildren());
		assertEquals(1, document2.getChild(2).numberOfChildren());
		
		assertEquals(true,string.equals(SmartScriptTester.createOriginalDocumentBody(document2)));
	}
	
	private String loader(String filename) {
		String doc=null;
		
		try {
			doc=new String(Files.readAllBytes(Paths.get(filename)), StandardCharsets.UTF_8);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return doc;
	}
}
