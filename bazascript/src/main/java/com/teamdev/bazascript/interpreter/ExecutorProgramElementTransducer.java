package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

/**
 *
 */

public class ExecutorProgramElementTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    private final ScriptElement scriptElement;

    public ExecutorProgramElementTransducer(ScriptElement resolver,
                                            ScriptElementExecutorFactory factory) {
        this.scriptElement = Preconditions.checkNotNull(resolver);
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        ScriptElementExecutor elementExecutor = factory.create(scriptElement);

        boolean executeResult = elementExecutor.execute(inputChain, outputChain);

        return executeResult;
    }
}
