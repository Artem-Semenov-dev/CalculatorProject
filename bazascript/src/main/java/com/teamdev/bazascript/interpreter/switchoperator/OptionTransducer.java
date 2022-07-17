package com.teamdev.bazascript.interpreter.switchoperator;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

public class OptionTransducer implements Transducer<SwitchOperatorContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    OptionTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchOperatorContext outputChain) throws ExecutionException {

        ScriptElementExecutor expressionExecutor = factory.create(ScriptElement.EXPRESSION);

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (!outputChain.isParseOnly()) {
                if (outputChain.checkCondition(outputChain.getScriptContext().systemStack().current().result())) {
                    outputChain.setCaseExecuted();
                } else {
                    outputChain.getScriptContext().setParsingPermission(true);
                }
            }

            return true;
        }

        return false;
    }
}
