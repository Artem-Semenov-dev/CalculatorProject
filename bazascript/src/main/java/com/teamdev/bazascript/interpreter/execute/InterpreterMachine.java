package com.teamdev.bazascript.interpreter.execute;

import com.teamdev.bazascript.interpreter.VariableHolder;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.execute.InterpreterState.*;

public final class InterpreterMachine extends FiniteStateMachine<InterpreterState, VariableHolder> {

    private InterpreterMachine(TransitionMatrix<InterpreterState> matrix) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(PROGRAM, new ProgramTransducer());
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }

    public static InterpreterMachine create() {
        TransitionMatrix<InterpreterState> matrix =
                TransitionMatrix.<InterpreterState>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, PROGRAM)
                        .allowTransition(PROGRAM, FINISH)

                        .build();

        return new InterpreterMachine(matrix);
    }
}
