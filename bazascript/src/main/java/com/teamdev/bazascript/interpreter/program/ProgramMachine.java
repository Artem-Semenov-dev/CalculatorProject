package com.teamdev.bazascript.interpreter.program;

import com.teamdev.bazascript.interpreter.ProgramMemory;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.program.ProgramStates.*;

public final class ProgramMachine extends FiniteStateMachine<ProgramStates, ProgramMemory> {

    private ProgramMachine(TransitionMatrix<ProgramStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(STATEMENT, new StatementTransducer(factory));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(';'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static ProgramMachine create(MathElementResolverFactory factory) {
        TransitionMatrix<ProgramStates> matrix =
                TransitionMatrix.<ProgramStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, STATEMENT)
                        .allowTransition(STATEMENT, SEPARATOR, FINISH)
                        .allowTransition(SEPARATOR, STATEMENT, FINISH)

                        .build();

        return new ProgramMachine(matrix, factory);
    }
}
