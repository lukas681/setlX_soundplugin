package org.randoom.setlx.functions;

import java.util.List;
import org.randoom.setlx.exceptions.*;
import org.randoom.setlx.types.*;
import org.randoom.setlx.utilities.State;

//square(NumberValue,NumberValue,NumberValue) : 
//
public abstract class GfxXYRFunction extends GfxFunction{
    
    protected GfxXYRFunction( String name ){
        super(name);
        addParameter("x");
        addParameter("y");
        addParameter("r");    
    }

    public Value execute(State state, List<Value> args, List<Value> writeBackVars) throws SetlException {
        Double x = doubleFromValue(args.get(0));
        Double y = doubleFromValue(args.get(1));
        Double r = doubleFromValue(args.get(2));
        executeStdDrawFunction(x, y, r);
        return SetlBoolean.TRUE;
    }
    
    protected abstract void executeStdDrawFunction(Double x, Double y, Double r);
    
}
