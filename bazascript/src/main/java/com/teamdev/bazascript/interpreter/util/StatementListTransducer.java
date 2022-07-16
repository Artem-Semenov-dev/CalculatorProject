package com.teamdev.bazascript.interpreter.util;

import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

public class StatementListTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    public StatementListTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        ScriptElementExecutor executor = factory.create(ScriptElement.PROGRAM);

        return executor.execute(inputChain, outputChain.getScriptContext());
    }
}
