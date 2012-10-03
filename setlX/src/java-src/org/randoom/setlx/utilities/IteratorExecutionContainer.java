package org.randoom.setlx.utilities;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.expressions.Variable;
import org.randoom.setlx.types.Value;

import java.util.List;

public interface IteratorExecutionContainer {

    /* lastIterationValue is the very last value added to the environment
       before execution.                                                    */
    public Value execute(final Value lastIterationValue) throws SetlException;

    /* Gather all bound and unbound variables in this expression and its siblings
          - bound   means "assigned" in this expression
          - unbound means "not present in bound set when used"
          - used    means "present in bound set when used"
       NOTE: Use optimizeAndCollectVariables() when adding variables from
             sub-expressions
    */
    public abstract void collectVariablesAndOptimize (
        final List<Variable> boundVariables,
        final List<Variable> unboundVariables,
        final List<Variable> usedVariables
    );

}

