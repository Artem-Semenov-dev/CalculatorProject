package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.initvar.InitVarContext;
import com.teamdev.bazascript.interpreter.initvar.InitVarMachine;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;

public class InitVarExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public InitVarExecutor(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {
        InitVarContext initVarContext = new InitVarContext(output);

        InitVarMachine initVarMachine = InitVarMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        if (initVarMachine.run(inputChain, initVarContext)){

            if (output.isParseOnly()) {
                return true;
            }

            initVarContext.getScriptContext().memory()
                    .setVariable(initVarContext.getVariableName(), initVarContext.getVariableValue());

            return true;
        }

        return false;
    }
}
