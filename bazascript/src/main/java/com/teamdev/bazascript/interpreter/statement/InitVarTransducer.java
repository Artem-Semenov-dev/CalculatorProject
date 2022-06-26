package com.teamdev.bazascript.interpreter.statement;

import com.teamdev.bazascript.interpreter.initvar.InitVarContext;
import com.teamdev.bazascript.interpreter.initvar.InitVarMachine;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

public class InitVarTransducer implements Transducer<ScriptContext> {

    private final ScriptElementExecutorFactory factory;

    InitVarTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ResolvingException {

        InitVarMachine variableInitMachine = InitVarMachine.create(factory);

        InitVarContext initVarContext = new InitVarContext(outputChain);

        return variableInitMachine.run(inputChain, initVarContext);
    }
}
