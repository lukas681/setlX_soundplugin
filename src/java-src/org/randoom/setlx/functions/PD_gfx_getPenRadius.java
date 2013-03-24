package org.randoom.setlx.functions;

import java.util.List;

import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.Real;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.StdDraw;


public class PD_gfx_getPenRadius extends GfxFunction {
    public final static PreDefinedFunction DEFINITION = new PD_gfx_getPenRadius();
    
    private PD_gfx_getPenRadius(){
        super("gfx_getPenRadius");
    }
    
    
    @Override
    protected Value execute(State state, List<Value> args, List<Value> writeBackVars) throws SetlException{
        return Real.valueOf(StdDraw.getPenRadius());
    }


   
}