package com.teamdev.bazascript.interpreter.util;

import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;

import java.util.List;

public class CodeBlockTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    public CodeBlockTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {
        FiniteStateMachine<Object, O, ExecutionException> machine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('{'),
                        new StatementListTransducer<O>(factory).named("Statement list inside braces"),
                        Transducer.checkAndPassChar('}')
                ));

        return machine.run(inputChain, outputChain);
    }
}
