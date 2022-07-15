package com.teamdev.bazascript.interpreter.string;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;

import java.util.List;

public class StringTransducer implements Transducer<ScriptContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, ScriptContext, ExecutionException> stringMachine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('\''), new StringInnerTransducer().named("String inner"), Transducer.checkAndPassChar('\''))
        );

        return stringMachine.run(inputChain, outputChain);
    }
}
