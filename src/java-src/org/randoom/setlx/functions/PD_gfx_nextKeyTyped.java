package org.randoom.setlx.functions;

import java.util.List;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.StdDraw;

public class PD_gfx_nextKeyTyped extends PreDefinedFunction {
    public final static PreDefinedFunction DEFINITION = new PD_gfx_nextKeyTyped();

    private PD_gfx_nextKeyTyped() {
        super("gfx_nextKeyTyped");
    }

    @Override
    public Value execute(final State state, final List<Value> args, final List<Value> writeBackVars) throws SetlException {
        return new SetlString( StdDraw.nextKeyTyped() );
    }
}