package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

/**
 * {@code InitialiseVariableTransducer} is an implementation of {@link Transducer} that
 * create and execute {@link ScriptElementExecutor} for variable initialisation.
 */

class InitialiseVariableTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    InitialiseVariableTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        outputChain.setInitialParsingStatus(outputChain.isParseOnly());

        ScriptElementExecutor initialiseVariableExecutor = factory.create(ScriptElement.INIT_VAR);

        return initialiseVariableExecutor.execute(inputChain, outputChain.getScriptContext());
    }
}
