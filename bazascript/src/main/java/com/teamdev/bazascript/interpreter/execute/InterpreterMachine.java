package com.teamdev.bazascript.interpreter.execute;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.execute.InterpreterState.*;

public final class InterpreterMachine extends FiniteStateMachine<InterpreterState, ScriptContext> {

    private InterpreterMachine(TransitionMatrix<InterpreterState> matrix, ScriptElementExecutorFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(PROGRAM, new ProgramTransducer(factory.create(ScriptElement.PROGRAM)));
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }

    public static InterpreterMachine create(ScriptElementExecutorFactory factory) {
        TransitionMatrix<InterpreterState> matrix =
                TransitionMatrix.<InterpreterState>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, PROGRAM)
                        .allowTransition(PROGRAM, FINISH)

                        .build();

        return new InterpreterMachine(matrix, factory);
    }
}
