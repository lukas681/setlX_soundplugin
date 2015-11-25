package org.randoom.setlx.operators;

import org.randoom.setlx.operatorUtilities.Stack;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Term;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.CodeFragment;
import org.randoom.setlx.utilities.State;

import java.util.List;

/**
 * Wrapper Expression for SetlX Literals, which parses escape sequences at runtime.
 */
public class LiteralConstructor extends AZeroOperator {
    private final String     originalLiteral;
    private final SetlString runtimeString;

    /**
     * Constructor, which parses escape sequences in the literal to create.
     *
     * @param originalLiteral String read by the parser.
     */
    public LiteralConstructor(final String originalLiteral) {
        this(originalLiteral, SetlString.parseLiteral(originalLiteral));
    }

    private LiteralConstructor(final String originalLiteral, final SetlString runtimeString) {
        this.originalLiteral = originalLiteral;
        this.runtimeString   = runtimeString;
    }

    @Override
    public SetlString evaluate(State state, Stack<Value> values)  {
        return runtimeString;
    }

    @Override
    public boolean collectVariablesAndOptimize (
        final State        state,
        final List<String> boundVariables,
        final List<String> unboundVariables,
        final List<String> usedVariables
    ) {
        return true;
    }

    /* string operations */

    @Override
    public void appendOperatorSign(final State state, final StringBuilder sb) {
        sb.append(originalLiteral);
    }

    /* term operations */

    @Override
    public Term modifyTerm(final State state, Term term) {
        term.addMember(state, runtimeString);
        return term;
    }

    /* comparisons */

    @Override
    public int compareTo(final CodeFragment other) {
        if (this == other) {
            return 0;
        } else if (other.getClass() == LiteralConstructor.class) {
            return originalLiteral.compareTo(((LiteralConstructor) other).originalLiteral);
        } else {
            return (this.compareToOrdering() < other.compareToOrdering())? -1 : 1;
        }
    }

    private final static long COMPARE_TO_ORDER_CONSTANT = generateCompareToOrderConstant(LiteralConstructor.class);

    @Override
    public long compareToOrdering() {
        return COMPARE_TO_ORDER_CONSTANT;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj.getClass() == LiteralConstructor.class) {
            return originalLiteral.equals(((LiteralConstructor) obj).originalLiteral);
        }
        return false;
    }

    @Override
    public int computeHashCode() {
        return ((int) COMPARE_TO_ORDER_CONSTANT) + originalLiteral.hashCode();
    }
}
