package com.teamdev.bazascript.interpreter.raeddatastructure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;

/**
 * {@code DataStructureReadExecutor} is an implementation of {@link ScriptElementExecutor}
 * that create and run {@link DataStructureReadMachine}.
 */

public class DataStructureReadExecutor implements ScriptElementExecutor {

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        DataStructureReadMachine machine = DataStructureReadMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        return machine.run(inputChain, output);
    }
}
