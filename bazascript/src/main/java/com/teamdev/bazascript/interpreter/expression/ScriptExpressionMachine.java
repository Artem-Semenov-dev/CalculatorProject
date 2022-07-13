package com.teamdev.bazascript.interpreter.expression;

import com.teamdev.bazascript.interpreter.util.ExecutorProgramElementTransducer;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;


public final class ScriptExpressionMachine extends FiniteStateMachine<ExpressionStates, ScriptContext, ExecutionException> {

    public static ScriptExpressionMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower){

        TransitionMatrix<ExpressionStates> matrix =
                TransitionMatrix.<ExpressionStates>builder()
                        .withStartState(ExpressionStates.START)
                        .withFinishState(ExpressionStates.FINISH)
                        .withTemporaryState(ExpressionStates.LOGICAL_EXPRESSION)
                        .allowTransition(ExpressionStates.START, ExpressionStates.LOGICAL_EXPRESSION, ExpressionStates.NUMERIC_EXPRESSION)
                        .allowTransition(ExpressionStates.LOGICAL_EXPRESSION, ExpressionStates.FINISH)
                        .allowTransition(ExpressionStates.NUMERIC_EXPRESSION, ExpressionStates.FINISH)

                        .build();

        return new ScriptExpressionMachine(matrix, exceptionThrower,  factory);
    }


    private ScriptExpressionMachine(TransitionMatrix<ExpressionStates> matrix,
                                    ExceptionThrower<ExecutionException> exceptionThrower,
                                    ScriptElementExecutorFactory factory) {

        super(matrix, exceptionThrower, true);

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
        registerTransducer(ExpressionStates.LOGICAL_EXPRESSION,
                new ExecutorProgramElementTransducer(ScriptElement.LOGICAL_EXPRESSION, factory));
        registerTransducer(ExpressionStates.NUMERIC_EXPRESSION,
                new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, factory));
    }
}
