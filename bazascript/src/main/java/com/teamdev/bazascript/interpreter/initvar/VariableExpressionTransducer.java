package com.teamdev.bazascript.interpreter.initvar;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.type.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.BiConsumer;

/**
 * {@code VariableExpressionTransducer} is an implementation of {@link Transducer}
 * that call execute method of {@link ScriptElementExecutor}, take and put result of execution
 * to any output chain that implements {@link WithContext}.
 */

class VariableExpressionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(VariableExpressionTransducer.class);

    private final ScriptElementExecutor expressionExecutor;

    private final BiConsumer<O, Value> setVariableValue;

    VariableExpressionTransducer(ScriptElementExecutor executor, BiConsumer<O, Value> setVariableValue) {
        this.expressionExecutor = Preconditions.checkNotNull(executor);
        this.setVariableValue = setVariableValue;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {


        Preconditions.checkNotNull(outputChain.getScriptContext(), "output is null");

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseonly()) {
                return true;
            }

            Value variableValue = outputChain.getScriptContext().systemStack().current().popResult();

            setVariableValue.accept(outputChain, variableValue);

            return true;
        }

        return false;
    }
}
