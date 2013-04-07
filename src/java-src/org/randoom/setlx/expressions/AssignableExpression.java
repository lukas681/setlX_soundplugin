package org.randoom.setlx.expressions;

import java.util.List;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.exceptions.UndefinedOperationException;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.VariableScope;

public abstract class AssignableExpression extends Expr {

    /**
     * Gather all bound and unbound variables in this expression and its siblings,
     * when it is used as an assignment.
     *
     * @see org.randoom.setlx.utilities.CodeFragment#collectVariablesAndOptimize(List<String>, List<String>, List<String>)
     *
     * @param boundVariables   Variables "assigned" in this fragment.
     * @param unboundVariables Variables not present in bound when used.
     * @param usedVariables    Variables present in bound when used.
     */
    public abstract void collectVariablesWhenAssigned (
        final List<String> boundVariables,
        final List<String> unboundVariables,
        final List<String> usedVariables
    );

    /**
     * Sets this expression to the given value.
     * Only makes sense for assignable expressions, like variables and id-lists.
     *
     * @param state          Current state of the running setlX program.
     * @param value          Value to assign.
     * @return               Clone of assigned value.
     * @throws SetlException Thrown in case of some (user-) error.
     */
    public final Value assign(final State state, final Value value) throws SetlException {
        assignUncloned(state, value);
        return value.clone();
    }

    /**
     * Sets this expression to the given value. Does not clone 'value' and does
     * not return 'value' for chained assignment.
     *
     * Only makes sense for assignable expressions, like variables and id-lists.
     *
     * @param state          Current state of the running setlX program.
     * @param value          Value to assign.
     * @throws SetlException Thrown in case of some (user-) error.
     */
    public abstract void assignUncloned(final State state, final Value value) throws SetlException;

    /**
     * Sets this expression to the given value. Does not clone 'value' and does
     * not return 'value' for chained assignment.
     * Also checks if the variable is already defined in scopes up to
     * (but EXCLUDING) 'outerScope'.
     * Returns true and sets 'v' if variable is undefined or already equal to 'v'.
     * Returns false, if variable is defined and different from 'v'.
     *
     * Only makes sense for assignable expressions, like variables and id-lists.
     *
     * @param state          Current state of the running setlX program.
     * @param value          Value to assign.
     * @param outerScope     Root scope of scopes to check.
     * @return               True, if variable is undefined or already equal to 'v'.
     * @throws SetlException Thrown in case of some (user-) error.
     */
    public boolean assignUnclonedCheckUpTo(
        final State         state,
        final Value         value,
        final VariableScope outerScope
    ) throws SetlException {
        throw new UndefinedOperationException(
            "Error in \"" + this + "\":" + state.getEndl() +
            "This expression can not be used as target for this kind of assignments."
        );
    }

    /*package*/ abstract Value evaluateUnCloned(final State state) throws SetlException;

}