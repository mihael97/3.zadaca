package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TypeToken;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * Parser
 *
 * @author Mihael
 */
public class SmartScriptParser {
    /**
     * Lexer
     */
    private SmartScriptLexer lexer;

    /**
     * Stack with nodes
     */
    private ObjectStack stack;

    /**
     * Constructor
     *
     * @param input input String
     * @throws NullPointerException if parameter is null
     */
    public SmartScriptParser(String input) {
        if (input == null) {
            throw new NullPointerException( "Naziv je null!" );
        }

        try {
            lexer = new SmartScriptLexer( input );
            getTokens();
        } catch (LexerException e) {
            throw new SmartScriptParserException( e.getMessage(), e );
        }
    }

    /**
     * Generates document tree from lexer's tokens
     */
    private void getTokens() {
        Token token;
        stack = new ObjectStack();
        stack.push( new DocumentNode() ); // stavljamo na stog dokument

        while ((token = lexer.getToken()) != null) {
            if (token.getType() == TypeToken.TAGSTART) {
                ArrayIndexedCollection collection = new ArrayIndexedCollection( 2 );

                lexer.setState();
                token = lexer.getToken();

                if (!checkID( token )) {
                    throw new SmartScriptParserException( "Nemoguce prepoznati ime taga!" );
                }

                collection.add( token );

                while ((token = lexer.getToken()) != null && token.getType() != TypeToken.TAGEND) {
                    collection.add( token );
                }

                if (token == null) {
                    break;
                }

                lexer.setState();

                tagFunction( collection );

            } else if (token.getType() == TypeToken.END) {
                endTag();
            } else {
                TextNode node = new TextNode( (String) token.getValue() );

                addNode( node );
            }
        }

        checkNumberOfNonEmptyLoops();
    }

    /**
     * Checks if all non-empty tags are closed
     *
     * @throws SmartScriptParserException if there are non-empty tags
     */
    private void checkNumberOfNonEmptyLoops() {
        if (stack.size() != 1) {
            throw new SmartScriptParserException(
                    "U programu je nedovoljno oznaka koje zatvaraju neprazne tagove! Na stogu je " + stack.size() );
        }
    }

    /**
     * Checks if tag name is allowed
     *
     * @param token token
     * @return true if token value is valid,otherwise false
     */
    private boolean checkID(Token token) {

        return token.getValue().toString().trim().equals( "=" )
                || token.getValue().toString().toUpperCase().trim().equals( "FOR" );
    }

    /**
     * Calls function for FOR or ECHO node creating
     *
     * @param collection collection of elements
     */
    private void tagFunction(ArrayIndexedCollection collection) {
        Token element = (Token) collection.get( 0 );

        switch (element.toString().toUpperCase()) {
            case "=":
                echo( collection );

                break;
            case "FOR":
                FORLoop( collection );

                break;
            default:
                break;
        }
    }

    /**
     * Adds children to last added stack's object
     *
     * @param node node we want to add
     */
    private void addNode(Node node) {
        Node fromStack = (Node) stack.pop();
        fromStack.addChildNode( node );
        stack.push( fromStack );
    }

    /**
     * Generates echo node from tokens
     *
     * @param collection collection of Elements
     **/
    private void echo(ArrayIndexedCollection collection) {
        ArrayIndexedCollection forReturn = new ArrayIndexedCollection( collection.size() );

        for (int i = 1; i < collection.size(); i++) {
            Element ele = makeElement( (Token) collection.get( i ) );
            forReturn.add( ele );
        }

        EchoNode node = new EchoNode( copyArray( forReturn.toArray(), collection.size() ) );

        addNode( node );
    }

    /**
     * Coverts Object array into Element array
     *
     * @param array Object array
     * @param size  array size
     * @return Element array
     */
    private Element[] copyArray(Object[] array, int size) {
        Element[] forReturn = new Element[size];
        int index = 0;

        for (Object obj : array) {
            if (obj != null) {
                forReturn[index++] = (Element) obj;
            } else {
                break;
            }
        }

        return forReturn;
    }

    /**
     * Generates FORLoop
     *
     * @param collection collection of elements which FORLoop contains
     * @return {@link ForLoopNode} generated ForLoopNode
     * @throws SmartScriptParserException - if one of arguments if function or there is invalid number of arguments
     */
    private void FORLoop(ArrayIndexedCollection collection) throws SmartScriptParser {

        if (collection.size() > 5) {
            throw new SmartScriptParserException( "Previse argumenata!" );

        } else if (collection.size() < 4) {
            throw new SmartScriptParserException( "Premalo argumenata!" );

        }

        String value = collection.get( 1 ).toString();
        ElementVariable variable = new ElementVariable( checkName( value ) );
        Element startExpression = makeElement( (Token) collection.get( 2 ) );
        Element endExpression = makeElement( (Token) collection.get( 3 ) );
        Element stepExpression = null;

        if (collection.size() == 5) {
            stepExpression = makeElement( (Token) collection.get( 4 ) );
        }

        // argument cannot be function
        if (startExpression instanceof ElementFunction || endExpression instanceof ElementFunction
                || stepExpression instanceof ElementFunction) {
            throw new SmartScriptParserException( "Jedan od argumenata je funkcija u FOR petlji!" );
        }

        ForLoopNode node = new ForLoopNode( variable, startExpression, endExpression, stepExpression );
        addNode( node );
        stack.push( node );
    }

    /**
     * Checks if token is end tag
     *
     * @throws SmartScriptParserException if stack is empty after stack pop
     */
    private void endTag() throws SmartScriptParser {
        Node node = (Node) stack.pop();

        if (stack.size() == 0) {
            throw new SmartScriptParserException( "Stog je ostao prazan,previse {$END$} u odnosu na neprazne tagove!" );
        }
    }

    /**
     * Generates element from token
     *
     * @param token token from lexer
     * @return element form token
     * @throws SmartScriptParser if variable's name is not valid or token cannot be parsed in any type
     */
    private Element makeElement(Token token) throws SmartScriptParser {
        Element element = null;

        switch (token.getType()) {
            case INTEGER:
                element = new ElementConstantInteger( (Integer) token.getValue() );
                break;

            case DOUBLE:
                element = new ElementConstantDouble( (Double) token.getValue() );

                break;
            case STRING:
                element = new ElementString( (String) token.getValue() );

                break;
            case OPERATOR:
                element = new ElementOperator( (String) token.getValue() );

                break;
            case FUNCTION:
                element = new ElementFunction( (String) token.getValue() );

                break;
            case VARIABLE:
                if (!checkName( (String) token.getValue() )) {
                    throw new SmartScriptParserException(
                            "Ime varijable " + value + " se ne moze prihvatiti!" )
                }
                element = new ElementVariable( (String) token.getValue() );

                break;
            default:
                throw new SmartScriptParserException(
                        "Nemoguce pretvoriti u niti jedan element! Razlog:" + token.getValue() );

        }

        return element;
    }

    /**
     * Checks if variable and function's name are in valid form<br>
     * Name is in valid form if it starts with letter and after begin contans only letter,numbers and underscores
     *
     * @param value input String
     * @return if name is valid true,otherwise false
     */
    private boolean checkName(String value) {
        char[] nameArray = value.toCharArray();
        SmartScriptParserException exception = new S;

        if (!Character.isLetter( nameArray[0] )) {
            return false;
        } else {
            for (int i = 1; i < nameArray.length; i++) {
                char c = nameArray[i];
                if (!(Character.isLetter( c ) || Character.isDigit( c ) || c == '_')) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns main DocumentNode
     *
     * @return {@link DocumentNode} document node
     * @throws SmartScriptParserException document tree doesn't exist
     */
    public DocumentNode getDocumentNode() throws SmartScriptParser {
        if (stack == null) {
            throw new SmartScriptParserException( "Stog nije inicijaliziran!" );
        } else {
            Node node = null;

            while (stack.size() != 0) {
                node = (Node) stack.pop();
            }

            return (DocumentNode) node;
        }
    }
}
