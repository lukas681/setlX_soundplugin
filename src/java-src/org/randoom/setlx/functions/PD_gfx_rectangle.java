package org.randoom.setlx.functions;


import java.util.List;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.StdDraw;

public class PD_gfx_rectangle extends GfxFunction {
    public final static PreDefinedFunction DEFINITION = new PD_gfx_rectangle();
    
    public PD_gfx_rectangle(){
        super("gfx_rectangle");
        addParameter("x");
        addParameter("y");
        addParameter("halfWidth");
        addParameter("halfHeight");
    }
    

    @Override
    protected Value execute(State state, List<Value> args, List<Value> writeBackVars) throws SetlException{
        StdDraw.rectangle( doubleFromValue( args.get(0) ),
                           doubleFromValue( args.get(1) ),
                           doubleFromValue( args.get(2) ),
                           doubleFromValue( args.get(3) )
                         );    
        return SetlBoolean.TRUE;
    }
}
