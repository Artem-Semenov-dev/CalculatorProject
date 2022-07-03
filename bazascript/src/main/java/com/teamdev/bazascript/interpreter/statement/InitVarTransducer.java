package com.teamdev.bazascript.interpreter.statement;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.initvar.InitVarContext;
import com.teamdev.bazascript.interpreter.initvar.InitVarMachine;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

public class InitVarTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    InitVarTransducer(ScriptElementExecutorFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, outputChain);

        InitVarMachine variableInitMachine = InitVarMachine.create(factory,
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                });

        InitVarContext initVarContext = new InitVarContext(outputChain);

        return variableInitMachine.run(inputChain, initVarContext);
    }
}