package com.teamdev.bazascript.interpreter.switchoperator;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.bazascript.interpreter.util.StatementListTransducer;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;

import java.util.List;

/**
 * {@code SwitchStatementListTransducer} is an implementation of {@link Transducer}
 * that create and run machine for parsing statement list inside braces for switch operator case.
 */

class SwitchStatementListTransducer implements Transducer<SwitchOperatorContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    SwitchStatementListTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchOperatorContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, SwitchOperatorContext, ExecutionException> machine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('{'),
                        new StatementListTransducer<>(factory),
                        Transducer.checkAndPassChar('}'),
                        new ChangeParsePermissionTransducer<>(outputChain.isCaseExecuted())
                ));

        return machine.run(inputChain, outputChain);
    }
}
