package org.randoom.setlx.functions;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.SetlDouble;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.Checker;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;

/**
 * stat_chiSquared(x, k):
 *                  Computes the chiSquared distribution with 'k' degrees of freedom.
 */
public class PD_stat_chiSquared extends PreDefinedProcedure {

    private final static ParameterDefinition X = createParameter("x");
    private final static ParameterDefinition K = createParameter("k");

    /** Definition of the PreDefinedProcedure 'stat_chiSquared' */
    public final static PreDefinedProcedure DEFINITION = new PD_stat_chiSquared();

    private PD_stat_chiSquared() {
        super();
        addParameter(X);
        addParameter(K);
    }

    @Override
    public Value execute(State state, HashMap<ParameterDefinition, Value> args) throws SetlException {
        final Value x = args.get(X);
        final Value k = args.get(K);

        Checker.checkIfNumberAndGreaterZero(state, x);
        Checker.checkIfNaturalNumberAndGreaterZero(state, k);

        ChiSquaredDistribution csd = new ChiSquaredDistribution(k.toJDoubleValue(state));
        return SetlDouble.valueOf(csd.density(x.toJDoubleValue(state)));
    }
}
