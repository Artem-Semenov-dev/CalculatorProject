package com.teamdev.bazascript.interpreter.util;

import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

import java.util.function.BiConsumer;

public class FunctionTransducer<O extends WithContext> implements Transducer<O, ExecutionException>{

    private final BiConsumer<O, Double> consumer;

    private final ScriptElementExecutor expressionExecutor;

    public FunctionTransducer(BiConsumer<O, Double> consumer, ScriptElementExecutor expressionExecutor) {
        this.consumer = consumer;
        this.expressionExecutor = expressionExecutor;
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException{
        if (expressionExecutor.execute(inputChain, outputChain.getContext())) {

            double result = outputChain.getContext().systemStack().current().peekResult();

            consumer.accept(outputChain, result);

            return true;
        }

        return false;
    }
}
