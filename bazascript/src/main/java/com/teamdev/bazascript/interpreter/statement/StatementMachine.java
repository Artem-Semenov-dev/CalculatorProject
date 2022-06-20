package com.teamdev.bazascript.interpreter.statement;

import com.teamdev.bazascript.interpreter.VariableHolder;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.statement.StatementStates.*;


public final class StatementMachine extends FiniteStateMachine<StatementStates, VariableHolder> {

    private StatementMachine(TransitionMatrix<StatementStates> matrix) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(INIT_VAR, Transducer.illegalTransition());
        registerTransducer(PROCEDURE, Transducer.checkAndPassChar(';'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static StatementMachine create(){
        TransitionMatrix<StatementStates> matrix = TransitionMatrix.<StatementStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, INIT_VAR)
                .allowTransition(INIT_VAR, FINISH)
                .allowTransition(START, PROCEDURE)
                .allowTransition(PROCEDURE, FINISH)

                .build();

        return new StatementMachine(matrix);
    }
}
