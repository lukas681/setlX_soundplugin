package org.randoom.setlx.boolExpressions;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.TermConversionException;
import org.randoom.setlx.expressions.Expr;
import org.randoom.setlx.expressions.Variable;
import org.randoom.setlx.types.Om;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.Condition;
import org.randoom.setlx.utilities.Iterator;
import org.randoom.setlx.utilities.IteratorExecutionContainer;
import org.randoom.setlx.utilities.TermConverter;
import org.randoom.setlx.utilities.VariableScope;

import java.util.List;


/*
grammar rule:
boolExpr
    : 'forall' '(' iteratorChain '|' condition ')'
    | [...]
    ;

implemented here as:
                   ========-----     =========
                     mIterator       mCondition
*/

public class Forall extends Expr {
    // functional character used in terms (MUST be class name starting with lower case letter!)
    private final static String FUNCTIONAL_CHARACTER = "^forall";
    // precedence level in SetlX-grammar
    private final static int    PRECEDENCE           = 1900;

    private final Iterator  mIterator;
    private final Condition mCondition;

    private class Exec implements IteratorExecutionContainer {
        private final Condition     mCondition;
        public        SetlBoolean   mResult;
        public        VariableScope mScope;

        public Exec (final Condition condition) {
            mCondition = condition;
            mResult    = SetlBoolean.TRUE;
            mScope     = null;
        }

        public Value execute(final Value lastIterationValue) throws SetlException {
            mResult = mCondition.eval();
            if (mResult == SetlBoolean.FALSE) {
                mScope = VariableScope.getScope();  // save state where result is true
                return Om.OM.setBreak();            // stop iteration
            }
            return null;
        }

        /* Gather all bound and unbound variables in this expression and its siblings
              - bound   means "assigned" in this expression
              - unbound means "not present in bound set when used"
              - used    means "present in bound set when used"
           NOTE: Use optimizeAndCollectVariables() when adding variables from
                 sub-expressions
        */
        public void collectVariablesAndOptimize (
            final List<Variable> boundVariables,
            final List<Variable> unboundVariables,
            final List<Variable> usedVariables
        ) {
            mCondition.collectVariablesAndOptimize(boundVariables, unboundVariables, usedVariables);
        }
    }

    public Forall(final Iterator iterator, final Condition condition) {
        mIterator  = iterator;
        mCondition = condition;
    }

    protected SetlBoolean evaluate() throws SetlException {
        final Exec e = new Exec(mCondition);
        mIterator.eval(e);
        if (e.mResult == SetlBoolean.FALSE && e.mScope != null) {
            // restore state in which mBoolExpr is false
            VariableScope.putAllValues(e.mScope);
        }
        return e.mResult;
    }

    /* Gather all bound and unbound variables in this expression and its siblings
          - bound   means "assigned" in this expression
          - unbound means "not present in bound set when used"
          - used    means "present in bound set when used"
       NOTE: Use optimizeAndCollectVariables() when adding variables from
             sub-expressions
    */
    protected void collectVariables (
        final List<Variable> boundVariables,
        final List<Variable> unboundVariables,
        final List<Variable> usedVariables
    ) {
        mIterator.collectVariablesAndOptimize(new Exec(mCondition), boundVariables, unboundVariables, usedVariables);

        // add dummy variable to prevent optimization, sideeffect variables cant be optimized
        unboundVariables.add(Variable.PREVENT_OPTIMIZATION_DUMMY);
    }

    /* string operations */

    public void appendString(final StringBuilder sb, final int tabs) {
        sb.append("forall (");
        mIterator.appendString(sb);
        sb.append(" | ");
        mCondition.appendString(sb, tabs);
        sb.append(")");
    }

    /* term operations */

    public Term toTerm() {
        final Term result = new Term(FUNCTIONAL_CHARACTER, 2);
        result.addMember(mIterator.toTerm());
        result.addMember(mCondition.toTerm());
        return result;
    }

    public static Forall termToExpr(final Term term) throws TermConversionException {
        if (term.size() != 2) {
            throw new TermConversionException("malformed " + FUNCTIONAL_CHARACTER);
        } else {
            final Iterator  iterator  = Iterator.valueToIterator(term.firstMember());
            final Condition condition = TermConverter.valueToCondition(term.lastMember());
            return new Forall(iterator, condition);
        }
    }

    // precedence level in SetlX-grammar
    public int precedence() {
        return PRECEDENCE;
    }
}

