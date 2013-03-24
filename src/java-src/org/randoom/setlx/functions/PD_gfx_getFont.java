package org.randoom.setlx.functions;

import java.util.List;


import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.types.SetlString;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;
import org.randoom.setlx.utilities.StdDraw;

public class PD_gfx_getFont extends GfxFunction {
    public final static PreDefinedFunction DEFINITION = new PD_gfx_getFont();
    
    private PD_gfx_getFont(){
        super("gfx_getFont");
    }
    
    
    @Override
    protected Value execute(State state, List<Value> args, List<Value> writeBackVars) throws SetlException{
        return new SetlString( StdDraw.getFont().getFontName() );
    }
}
