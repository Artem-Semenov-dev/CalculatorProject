package com.teamdev.bazascript.interpreter.initvar;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.type.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code VariableExpressionTransducer} is an implementation of {@link Transducer}
 * that call execute method of {@link ScriptElementExecutor}, take and put result of execution
 * to {@link InitVarContext}.
 */

public class VariableExpressionTransducer implements Transducer<InitVarContext, ExecutionException> {

    private final ScriptElementExecutor expressionExecutor;

    public VariableExpressionTransducer(ScriptElementExecutor executor) {
        this.expressionExecutor = Preconditions.checkNotNull(executor);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, InitVarContext outputChain) throws ExecutionException {


        Preconditions.checkNotNull(outputChain.getScriptContext(), "output is null");

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            Value variableValue = outputChain.getScriptContext().systemStack().current().result();

            outputChain.setVariableValue(variableValue);

            return true;
        }

        return false;
    }
}
