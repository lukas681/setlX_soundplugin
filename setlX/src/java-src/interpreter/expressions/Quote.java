package interpreter.expressions;

import interpreter.exceptions.SetlException;
import interpreter.exceptions.TermConversionException;
import interpreter.types.Term;
import interpreter.types.Value;
import interpreter.utilities.Environment;
import interpreter.utilities.TermConverter;

/*
grammar rule:
prefixOperation
    : [...]
    | '@' factor
    ;

implemented here as:
          ======
          mExpr
*/

public class Quote extends Expr {
    // functional character used in terms (MUST be class name starting with lower case letter!)
    private final static String FUNCTIONAL_CHARACTER = "^quote";
    // precedence level in SetlX-grammar
    private final static int    PRECEDENCE           = 1900;

    private Expr mExpr;
    private int  mLineNr;

    public Quote(Expr expr) {
        mExpr   = expr;
        mLineNr = -1;
    }

    public int getLineNr() {
        if (mLineNr < 0) {
            computeLineNr();
        }
        return mLineNr;
    }

    public void computeLineNr() {
        mLineNr = Environment.sourceLine;
        mExpr.computeLineNr();
    }

    public Value evaluate() throws SetlException {
        return mExpr.toTermEvalArguments();
    }

    /* string operations */

    public String toString(int tabs) {
        return "@" + mExpr.toString(tabs);
    }

    /* term operations */

    public Value toTerm() {
        return mExpr.toTermQuoted();
    }

    public Value toTermEvalArguments() throws SetlException {
        Term        result      = new Term(FUNCTIONAL_CHARACTER);
        result.addMember(mExpr.toTermEvalArguments());
        return result;
    }

    public Term toTermQuoted() {
        Term        result      = new Term(FUNCTIONAL_CHARACTER);
        result.addMember(mExpr.toTerm());
        return result;
    }

    public static Quote termToExpr(Term term) throws TermConversionException {
        if (term.size() != 1) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            Expr expr = TermConverter.valueToExpr(PRECEDENCE, false, term.firstMember());
            return new Quote(expr);
        }
    }

    // precedence level in SetlX-grammar
    public int precedence() {
        return PRECEDENCE;
    }
}
