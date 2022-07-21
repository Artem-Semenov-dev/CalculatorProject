package com.teamdev.bazascript.interpreter.forloop;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;

/**
 * {@code ForLoopExecutor} is an implementation of {@link ScriptElementExecutor}
 * that create and run {@link ForLoopMachine}.
 */

public class ForLoopExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public ForLoopExecutor(ScriptElementExecutorFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        ForLoopMachine forLoopMachine = ForLoopMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        ForLoopOutputChain forLoopOutputChain = new ForLoopOutputChain(output);

        return forLoopMachine.run(inputChain, forLoopOutputChain);
    }
}
