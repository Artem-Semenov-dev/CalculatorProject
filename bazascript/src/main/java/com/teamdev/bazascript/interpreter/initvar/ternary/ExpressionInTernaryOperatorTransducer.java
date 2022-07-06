package com.teamdev.bazascript.interpreter.initvar.ternary;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

public class ExpressionInTernaryOperatorTransducer implements Transducer<TernaryOperatorContext, ExecutionException> {

    private final ScriptElementExecutor executor;

    public ExpressionInTernaryOperatorTransducer(ScriptElementExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, TernaryOperatorContext outputChain) throws ExecutionException {
        Preconditions.checkNotNull(inputChain, outputChain);

        return executor.execute(inputChain, outputChain.getScriptContext());
    }
}
