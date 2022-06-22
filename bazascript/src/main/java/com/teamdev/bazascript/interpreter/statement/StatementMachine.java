package com.teamdev.bazascript.interpreter.statement;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.statement.StatementStates.*;

public final class StatementMachine extends FiniteStateMachine<StatementStates, ProgramMemory> {

    private StatementMachine(TransitionMatrix<StatementStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(INIT_VAR, new InitVarTransducer(factory));
        registerTransducer(PROCEDURE, new ProcedureTransducer(factory));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static StatementMachine create(MathElementResolverFactory factory){
        TransitionMatrix<StatementStates> matrix = TransitionMatrix.<StatementStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, INIT_VAR, PROCEDURE)
                .allowTransition(INIT_VAR, FINISH)
                .allowTransition(PROCEDURE, FINISH)

                .build();

        return new StatementMachine(matrix, factory);
    }
}
