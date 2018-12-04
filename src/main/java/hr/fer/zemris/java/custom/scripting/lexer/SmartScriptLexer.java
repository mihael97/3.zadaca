package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Lexer
 *
 * @author Mihael
 */
public class SmartScriptLexer {

    /**
     * Input in array format
     */
    private char[] input;

    /**
     * Index of next character
     */
    private int arrayIndex;

    /**
     * Current lexer's state
     */
    private LexerStates state;

    /**
     * Constructor
     *
     * @param input input String
     */
    public SmartScriptLexer(String input) {
        super();

        if (input == null || input.length() == 0) {
            throw new LexerException( "Duljina niza je 0 ili je sam argument null" );
        }

        this.input = cleanArray( input );

        arrayIndex = 0;
        state = LexerStates.NONTAG;
    }

    /**
     * Generates next token
     *
     * @return Token next token
     */
    public Token getToken() {
        if (arrayIndex + 1 < input.length) {
            char inputChar = input[arrayIndex];
            Token pom = null;

            if (inputChar == '{' && input[arrayIndex + 1] == '$') { // pocetak ili kraj funkcije

                if (!isEnd()) {
                    pom = new Token( TypeToken.TAGSTART,
                            new StringBuilder().append( inputChar ).append( input[++arrayIndex] ).toString() );
                    arrayIndex++;
                } else {

                    pom = new Token( TypeToken.END, "{$END$}" );
                    arrayIndex += 7;
                }

            } else if (state == LexerStates.TAG) {
                pom = functionWork();
            } else {

                pom = new Token( TypeToken.TEXT, readText() );
            }

            return pom;
        } else {
            return null;
        }
    }

    /**
     * Reads text if lexer is outside tags
     *
     * @return String from input
     */
    private String readText() {
        StringBuilder builder = new StringBuilder();
        char inputChar;

        while ((inputChar = input[arrayIndex]) != '{') {
            if (inputChar == '\\' && (input[arrayIndex + 1] == '\\' || input[arrayIndex + 1] == '{')) {
                builder.append( input[++arrayIndex] );
            } else {
                builder.append( inputChar );
            }

            if ((arrayIndex + 1) != input.length) {
                arrayIndex++;
            } else {
                break;
            }
        }

        return builder.toString();
    }

    /**
     * Checks if next tag is closing tag
     *
     * @return true if it is,otherwise false
     */
    private boolean isEnd() {
        String end = "{$END$}";
        int index = arrayIndex;
        int i = 0;
        int length = input.length;

        while ((index < length)) {
            char c = input[index++];
            if (c != ' ') {
                if (end.charAt( i ) == Character.toUpperCase( c )) {
                    if (++i == 6) {
                        break;
                    }
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Metoda koja racuna sljedeci token u slucaju da se lexer nalazi u stanju kada
     * vrijede druga leksicka pravila(stanje TAG)
     * Returns next token if lexer is in <code>TAG</code> state
     *
     * @return {@link Token} next token
     */
    private Token functionWork() {
        cleanSpaces();
        char inputChar;
        StringBuilder pom = new StringBuilder();

        while ((inputChar = input[arrayIndex]) != ' ') {
            if (inputChar == '$') {
                if (pom.length() == 0) {
                    pom.append( inputChar ).append( '}' );
                    arrayIndex += 2;
                }

                break;
            } else if (inputChar == '=') {
                pom.append( inputChar );
                arrayIndex++;
                break;
            } else if (inputChar == '\"') {
                if (pom.length() == 0) {
                    pom.append( readString() );
                }

                break;
            } else {
                pom.append( inputChar );
            }
            arrayIndex++;
        }

        return makeObject( pom.toString() );
    }

    /**
     * Reads characters until next '"'
     *
     * @return String from input
     */
    private String readString() {
        char inputChar = input[arrayIndex++];
        StringBuilder builder = new StringBuilder().append( inputChar );

        while ((inputChar = input[arrayIndex]) != '\"') {
            if (inputChar == '\\' && (arrayIndex + 1) != input.length) {
                builder.append( input[++arrayIndex] );

            } else {
                builder.append( inputChar );
            }

            if ((arrayIndex + 1) == input.length) {
                break;
            } else {
                arrayIndex++;
            }
        }

        builder.append( inputChar );
        arrayIndex++;
        return builder.toString();
    }

    /**
     * Creates token
     *
     * @param string context from input
     * @return {@link Token} generated token
     */
    private Token makeObject(String string) {
        Token forReturn = null;
        if (string == null) {
            throw new NullPointerException();
        } else if (string.equals( "$}" )) {
            forReturn = new Token( TypeToken.TAGEND, string );
        } else if (string.startsWith( "\"" )) {
            forReturn = new Token( TypeToken.STRING, string );
        } else if (Character.isDigit( string.charAt( 0 ) )
                || string.charAt( 0 ) == '-' && string.length() != 1 && Character.isDigit( string.charAt( 1 ) )) {
            // drugi dio uvjeta ako je negativan broj
            try {
                if (string.contains( "." )) {
                    forReturn = new Token( TypeToken.DOUBLE, Double.parseDouble( string ) );
                } else {
                    forReturn = new Token( TypeToken.INTEGER, Integer.parseInt( string ) );
                }
            } catch (NumberFormatException e) {
                throw new LexerException( "Nemoguce parsirati!" );
            }
        } else if (isOperator( string )) {
            forReturn = new Token( TypeToken.OPERATOR, string );
        } else if (isFunction( string )) {
            forReturn = new Token( TypeToken.FUNCTION, string.substring( 1, string.length() ) ); // micemo @ iz znaka
        } else {
            forReturn = new Token( TypeToken.VARIABLE, string );
        }

        return forReturn;
    }

    /**
     * Checks if input token is formula(formula starts wiht <code>@</code>)
     *
     * @param string input string
     * @return true if string is function name,otherwise false
     */
    private boolean isFunction(String string) {
        return string.startsWith( "@" );
    }

    /**
     * Sets lexer state<br>
     * If input token is <code>FOR</code>, state is <code>FORLOOP</code> and if token is <code>=</code>, next state is <code>ECHO</code>. Otherwise, state is <code>NONTAG</code>
     */
    public void setState() {
        if (state == LexerStates.NONTAG) {
            state = LexerStates.TAG;
        } else {
            state = LexerStates.NONTAG;
        }
    }

    /**
     * Skips blanks
     */
    private void cleanSpaces() {
        while (input[arrayIndex] == ' ') {
            arrayIndex++;
        }
    }

    /**
     * Current lexer's state(TAG ili NONTAG)
     *
     * @return current LexerState
     */
    public LexerStates getState() {
        return state;
    }

    /**
     * Checks if symbol is operator(+,-,/,^)
     *
     * @param operator operator in String format
     * @return true is string is operator,otherwise false
     */
    private boolean isOperator(String operator) {
        return operator.equals( "+" ) || operator.equals( "-" ) || operator.equals( "/" ) || operator.equals( "^" )
                || operator.equals( "*" ))

    }

    /**
     * Checks if input String is in valid form
     *
     * @param string input string
     * @return String we starts with in analyze
     * @throws LexerException not allowed characters oreder
     */
    private char[] cleanArray(String string) {
        StringBuilder builder = new StringBuilder();

        char[] array = string.toCharArray();
        boolean tags = false;

        for (int i = 0, length = string.length(); i < length; i++) {

            if (tags == false) {
                if (array[i] == '\\') {
                    if (((i + 1) != length && (array[i + 1] == '{' || array[i + 1] == '\\'))) {
                        builder.append( array[i] );
                        i++;
                    } else {
                        throw new LexerException( "Pogreska kod dijela '\\' na indexu " + i );
                    }
                } else if (array[i] == '{') {
                    if ((i + 1) != length && array[i + 1] == '$') {
                        tags = true;
                    } else {
                        throw new LexerException( "Pogreska kod '{' na indexu " + i );
                    }

                }

            } else {
                if (array[i] == '\\' && (i + 1) != length) {
                    if (array[i + 1] == '\\') {
                        builder.append( array[i] );
                        i++;
                    } else if (array[i + 1] == '\"') {
                        builder.append( array[i] );
                        i++;
                    } else {
                        throw new LexerException( "Pogreska kod '\\' na indexu " + i );
                    }
                } else if (array[i] == '}') {
                    if ((i - 1) != 0 && array[i - 1] == '$') {
                        tags = false;
                    } else {
                        throw new LexerException( "Pogreska kod '}' na indexu " + i );
                    }
                } else if (array[i] == '{') {
                    throw new LexerException( "Tag unutar taga!" );
                }

            }

            builder.append( array[i] );

        }

        return builder.toString().toCharArray();
    }

}
