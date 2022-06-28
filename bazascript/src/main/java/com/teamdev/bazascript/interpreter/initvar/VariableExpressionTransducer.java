package com.teamdev.bazascript.interpreter.initvar;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@code VariableExpressionTransducer} is an implementation of {@link Transducer}
 * that call execute method of {@link ScriptElementExecutor}, take and put result of execution
 * to {@link InitVarContext}.
 */

public class VariableExpressionTransducer implements Transducer<InitVarContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(VariableExpressionTransducer.class);

    private final ScriptElementExecutor expressionExecutor;

    VariableExpressionTransducer(ScriptElementExecutor executor) {
        this.expressionExecutor = executor;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, InitVarContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(outputChain.getContext(), "output is null");

        if (expressionExecutor.execute(inputChain, outputChain.getContext())) {

            double variableValue = outputChain.getContext().systemStack().current().peekResult();

            outputChain.setVariableValue(variableValue);

            return true;
        }

        return false;
    }
}
