package com.teamdev.bazascript.interpreter.raeddatastructure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.bazascript.interpreter.raeddatastructure.DataStructureReadStates.*;

public final class DataStructureReadMachine extends FiniteStateMachine<DataStructureReadStates, ScriptContext, ExecutionException> {

    public static DataStructureReadMachine create(ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<DataStructureReadStates> matrix =
                TransitionMatrix.<DataStructureReadStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)

                        .withTemporaryState(DATA_STRUCTURE_NAME)

                        .allowTransition(START, DATA_STRUCTURE_NAME)
                        .allowTransition(DATA_STRUCTURE_NAME, DOT)
                        .allowTransition(DOT, DATA_STRUCTURE_FIELD)
                        .allowTransition(DATA_STRUCTURE_FIELD, FINISH)

                        .build();

        return new DataStructureReadMachine(matrix, exceptionThrower);
    }

    private DataStructureReadMachine(TransitionMatrix<DataStructureReadStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(DATA_STRUCTURE_NAME, new DataStructureNameTransducer());
        registerTransducer(DATA_STRUCTURE_FIELD, new DataStructureFieldTransducer());
        registerTransducer(DOT, Transducer.checkAndPassChar('.'));
    }
}
