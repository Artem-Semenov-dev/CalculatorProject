package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;

public class FunctionExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutor factoryExecutor;

    public FunctionExecutor(ScriptElementExecutor factoryExecutor) {
        this.factoryExecutor = factoryExecutor;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        return factoryExecutor.execute(inputChain, output);
    }
}
