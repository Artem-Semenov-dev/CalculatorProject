package com.teamdev.bazascript.interpreter.switchoperator;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;

import java.util.List;

public class SwitchVariableTransducer implements Transducer<SwitchOperatorContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    SwitchVariableTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchOperatorContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, SwitchOperatorContext, ExecutionException> machine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('('),
                        new ComparedValueTransducer(factory).named("Variable inside switch operator"),
                        Transducer.checkAndPassChar(')'))
        );

        return machine.run(inputChain, outputChain);
    }
}
