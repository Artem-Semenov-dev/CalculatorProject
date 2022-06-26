package com.teamdev.bazascript.interpreter.program;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

class StatementTransducer implements Transducer<ScriptContext> {

    private final ScriptElementExecutor executor;

    public StatementTransducer(ScriptElementExecutor executor) {

        this.executor = Preconditions.checkNotNull(executor);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ResolvingException {

        return executor.execute(inputChain, outputChain);
    }
}
