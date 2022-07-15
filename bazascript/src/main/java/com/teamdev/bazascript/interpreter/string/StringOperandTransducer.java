package com.teamdev.bazascript.interpreter.string;

import com.teamdev.bazascript.interpreter.executors.ExecutorProgramElementTransducer;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;

public class StringOperandTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    public StringOperandTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, ScriptContext, ExecutionException> machine = FiniteStateMachine.oneOfMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                new StringTransducer(),
                new ExecutorProgramElementTransducer(ScriptElement.NUMBER, factory).named("Number in string expression"),
                new ExecutorProgramElementTransducer(ScriptElement.BRACKETS, factory).named("Brackets in string expression"),
                new ExecutorProgramElementTransducer(ScriptElement.READ_VARIABLE, factory).named("Variable in string expression")
        );

        return machine.run(inputChain, outputChain);
    }
}
