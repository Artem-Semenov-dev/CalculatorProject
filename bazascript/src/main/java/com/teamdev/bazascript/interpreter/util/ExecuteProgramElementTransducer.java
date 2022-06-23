package com.teamdev.bazascript.interpreter.util;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;
import com.teamdev.fsm.Transducer;

import java.util.function.BiConsumer;

public class ExecuteProgramElementTransducer implements Transducer<ScriptContext> {

    private final ScriptElementExecutorFactory factory;

    private final ScriptElement scriptElement;

    private final BiConsumer<ScriptContext, Double> resultConsumer;

    public ExecuteProgramElementTransducer(ScriptElement resolver, BiConsumer<ScriptContext, Double> resultConsumer,
                                           ScriptElementExecutorFactory factory) {
        this.scriptElement = Preconditions.checkNotNull(resolver);
        this.resultConsumer = Preconditions.checkNotNull(resultConsumer);
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ResolvingException {

        ScriptElementExecutor elementExecutor = factory.create(scriptElement);

        boolean executeResult = elementExecutor.execute(inputChain, outputChain);

        if (executeResult){
            
        }

        return executeResult;
    }
}
