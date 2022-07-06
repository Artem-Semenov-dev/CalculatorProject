package com.teamdev.bazascript.interpreter.initvar.ternary;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

/**
 * {@code ExpressionInTernaryOperatorTransducer} is an implementation of {@link Transducer}
 * that call execute method of {@link ScriptElementExecutor}. That transducer used to execute
 * expression inside {@link TernaryOperatorMachine}.
 */

class ExpressionInTernaryOperatorTransducer implements Transducer<TernaryOperatorContext, ExecutionException> {

    private final ScriptElementExecutor executor;

    ExpressionInTernaryOperatorTransducer(ScriptElementExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, TernaryOperatorContext outputChain) throws ExecutionException {
        Preconditions.checkNotNull(inputChain, outputChain);

        return executor.execute(inputChain, outputChain.getScriptContext());
    }
}
