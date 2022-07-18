package com.teamdev.bazascript.interpreter.switchoperator;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

/**
 * {@code ComparedValueTransducer} is an implementation of {@link Transducer}
 * that produce compared value for switch operator to {@link SwitchOperatorContext}.
 */

class ComparedValueTransducer implements Transducer<SwitchOperatorContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    ComparedValueTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchOperatorContext outputChain) throws ExecutionException {
        ScriptElementExecutor variableExecutor = factory.create(ScriptElement.READ_VARIABLE);

        if (variableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            outputChain.setComparedValue(outputChain.getScriptContext().systemStack().current().result());
            return true;
        }

        return false;
    }
}
