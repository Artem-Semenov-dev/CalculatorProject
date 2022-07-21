package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;

public class ForLoopExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public ForLoopExecutor(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        ForLoopMachine forLoopMachine =  ForLoopMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        ForLoopOutputChain forLoopOutputChain = new ForLoopOutputChain(output);

        return forLoopMachine.run(inputChain, forLoopOutputChain);
    }
}
