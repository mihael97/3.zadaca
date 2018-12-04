package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * For loop<br>
 * For loop must have three parameters:<br>
 * <ul>
 * <li>variable name</li>
 * <li>initial expression</li>
 * <li>final expression</li>
 * </ul><br>
 * Loop can also have step expression parameter
 *
 * @author Mihael
 */
public class ForLoopNode extends Node {
    /**
     * Value
     */
    private ElementVariable variable;
    /**
     * Initial expression
     */
    private Element initialExpression;
    /**
     * Final expression
     */
    private Element finalExpression;
    /**
     * Loop expression
     */
    private Element stepExpression;

    /**
     * Constructor
     *
     * @param variable          variable name
     * @param initialExpression initial expression
     * @param finalExpression   final expression
     * @param stepExpression    step
     * @throws NullPointerException if one of first three parameters in null
     */
    public ForLoopNode(ElementVariable variable, Element initialExpression, Element finalExpression,
                       Element stepExpression) {
        super();
        if (variable == null || initialExpression == null || finalExpression == null) {
            throw new SmartScriptParserException( "Jedan od predanih argumenata je null!" );
        }

        this.variable = variable;
        this.initialExpression = initialExpression;
        this.finalExpression = finalExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * Variable name
     *
     * @return {@link ElementVariable} variable name
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Initial expression
     *
     * @return {@link Element} initial expression
     */
    public Element getInitialExpression() {
        return initialExpression;
    }

    /**
     * End expression
     *
     * @return {@link Element} end expression
     */
    public Element getFinalExpression() {
        return finalExpression;
    }

    /**
     * Loop step
     *
     * @return {@link Element}
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    /**
     * Returns String representation of FOR loop
     *
     * @return String string represenation
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder().append( "{$ FOR " ).append( variable.asText() ).append( " " );

        if (initialExpression instanceof ElementString) {
            string.append( ((ElementString) initialExpression).forParse() );
        } else {
            string.append( initialExpression.asText() );
        }

        string.append( " " );

        if (finalExpression instanceof ElementString) {
            string.append( ((ElementString) finalExpression).forParse() );
        } else {
            string.append( finalExpression.asText() );
        }

        string.append( " " );

        if (stepExpression != null) {
            if (stepExpression instanceof ElementString) {
                string.append( ((ElementString) stepExpression).forParse() );
            } else {
                string.append( stepExpression.asText() );
            }

            string.append( " " );
        }

        string.append( "$}" );

        return string.toString();
    }

}
