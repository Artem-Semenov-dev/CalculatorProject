package com.teamdev.bazascript.interpreter.program;

import com.teamdev.bazascript.interpreter.VariableHolder;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.program.ProgramStates.*;

public final class ProgramMachine extends FiniteStateMachine<ProgramStates, VariableHolder> {

    private ProgramMachine(TransitionMatrix<ProgramStates> matrix) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(STATEMENT, Transducer.illegalTransition());
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(';'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static ProgramMachine create() {
        TransitionMatrix<ProgramStates> matrix =
                TransitionMatrix.<ProgramStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, STATEMENT)
                        .allowTransition(STATEMENT, SEPARATOR, FINISH)
                        .allowTransition(SEPARATOR, STATEMENT)

                        .build();

        return new ProgramMachine(matrix);
    }
}
