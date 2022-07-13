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

/**
 * {@code ScriptExpressionMachine} is an implementation of {@link FiniteStateMachine}.
 * {@code ScriptExpressionMachine} is a one of machine that intended to choose between logical and numeric expression.
 */

public final class ScriptExpressionMachine extends FiniteStateMachine<ScriptExpressionStates, ScriptContext, ExecutionException> {

    public static ScriptExpressionMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower){

        TransitionMatrix<ScriptExpressionStates> matrix =
                TransitionMatrix.<ScriptExpressionStates>builder()
                        .withStartState(ScriptExpressionStates.START)
                        .withFinishState(ScriptExpressionStates.FINISH)
                        .withTemporaryState(ScriptExpressionStates.LOGICAL_EXPRESSION)
                        .allowTransition(ScriptExpressionStates.START, ScriptExpressionStates.LOGICAL_EXPRESSION, ScriptExpressionStates.NUMERIC_EXPRESSION)
                        .allowTransition(ScriptExpressionStates.LOGICAL_EXPRESSION, ScriptExpressionStates.FINISH)
                        .allowTransition(ScriptExpressionStates.NUMERIC_EXPRESSION, ScriptExpressionStates.FINISH)

                        .build();

        return new ScriptExpressionMachine(matrix, exceptionThrower,  factory);
    }


    private ScriptExpressionMachine(TransitionMatrix<ScriptExpressionStates> matrix,
                                    ExceptionThrower<ExecutionException> exceptionThrower,
                                    ScriptElementExecutorFactory factory) {

        super(matrix, exceptionThrower, true);

        registerTransducer(ScriptExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ScriptExpressionStates.FINISH, Transducer.autoTransition());
        registerTransducer(ScriptExpressionStates.LOGICAL_EXPRESSION,
                new ExecutorProgramElementTransducer(ScriptElement.LOGICAL_EXPRESSION, factory));
        registerTransducer(ScriptExpressionStates.NUMERIC_EXPRESSION,
                new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, factory));
    }
}
