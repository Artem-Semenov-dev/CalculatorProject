package com.teamdev.bazascript.interpreter.util;

import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.type.Value;

import java.util.function.BiConsumer;

public class FunctionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final BiConsumer<O, Value> consumer;

//    private final ScriptElementExecutor expressionExecutor;

    private final ScriptElementExecutorFactory factory;

    private final ScriptElement scriptElement;

    public FunctionTransducer(BiConsumer<O, Value> consumer, ScriptElementExecutorFactory factory, ScriptElement scriptElement) {
        this.consumer = consumer;
        this.factory = factory;
        this.scriptElement = scriptElement;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        ScriptElementExecutor expressionExecutor = factory.create(scriptElement);

        if (expressionExecutor.execute(inputChain, outputChain.getContext())) {

            Value result = outputChain.getContext().systemStack().current().peekResult();

            consumer.accept(outputChain, result);

            return true;
        }

        return false;
    }
}
