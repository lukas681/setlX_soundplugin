package org.randoom.setlx.functions;

import org.randoom.setlx.types.Rational;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.List;

// now()                   : get current time since epoch in ms

public class PD_now extends PreDefinedFunction {
    public final static PreDefinedFunction DEFINITION
                                            = new PD_now();

    private PD_now() {
        super("now");
    }

    @Override
    public Value execute(final State state,
                         final List<Value> args,
                         final List<Value> writeBackVars
    ) {
        return Rational.valueOf(state.currentTimeMillis());
    }
}
