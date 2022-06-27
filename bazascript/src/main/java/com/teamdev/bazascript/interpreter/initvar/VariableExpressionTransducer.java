package com.teamdev.bazascript.interpreter.initvar;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.calculator.ResolvingException;
import com.teamdev.fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VariableExpressionTransducer implements Transducer<InitVarContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(VariableExpressionTransducer.class);

    private final ScriptElementExecutor executor;

    VariableExpressionTransducer(ScriptElementExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, InitVarContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(outputChain.getContext(), "output is null");

        if (executor.execute(inputChain, outputChain.getContext())) {

            double variableValue = outputChain.getContext().systemStack().current().peekResult();

            outputChain.setVariableValue(variableValue);

            return true;
        }

        return false;
    }
}
