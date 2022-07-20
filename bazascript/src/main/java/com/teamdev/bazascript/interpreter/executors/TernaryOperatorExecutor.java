package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.initvar.ternary.TernaryOperatorContext;
import com.teamdev.bazascript.interpreter.initvar.ternary.TernaryOperatorMachine;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;

public class TernaryOperatorExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public TernaryOperatorExecutor(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {
        TernaryOperatorMachine ternaryOperatorMachine = TernaryOperatorMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        TernaryOperatorContext ternaryOperatorContext = new TernaryOperatorContext(output);

        return ternaryOperatorMachine.run(inputChain, ternaryOperatorContext);
    }
}
