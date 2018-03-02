package org.randoom.setlx.functions;

import org.randoom.setlx.SetlXMusic.SetlXRealTimePlayer.iRealTimePlayer;
import org.randoom.setlx.SetlXMusic.SoundPlugin;
import org.randoom.setlx.exceptions.SetlException;
import org.randoom.setlx.parameters.ParameterDefinition;
import org.randoom.setlx.types.SetlBoolean;
import org.randoom.setlx.types.Value;
import org.randoom.setlx.utilities.State;

import java.util.HashMap;

public class PD_stopTones extends PreDefinedProcedure {

    public final static PreDefinedProcedure DEFINITION = new PD_stopTones();

    private iRealTimePlayer rtplayer = SoundPlugin.getInstance().getSetlXRealTimePlayer();

    protected PD_stopTones() {
        super();
    }

    @Override
    protected Value execute(final State state, final HashMap<ParameterDefinition, Value> args) throws SetlException {
        rtplayer.stopNotes();
        return SetlBoolean.TRUE;
    }

}
