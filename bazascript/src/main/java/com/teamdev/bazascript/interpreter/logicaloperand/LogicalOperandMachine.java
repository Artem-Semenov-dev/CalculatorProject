package com.teamdev.bazascript.interpreter.logicaloperand;

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
 * {@code LogicalOperandMachine} is an implementation of {@link FiniteStateMachine}.
 * {@code LogicalOperandMachine} is a one of machine that intended to choose between possible operands of logical expression.
 */

import static com.teamdev.bazascript.interpreter.logicaloperand.LogicalOperandStates.*;

public final class LogicalOperandMachine extends FiniteStateMachine<LogicalOperandStates, ScriptContext, ExecutionException> {

    public static LogicalOperandMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<LogicalOperandStates> matrix =
                TransitionMatrix.<LogicalOperandStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .withTemporaryState(RELATIONAL_EXPRESSION)
                        .withTemporaryState(READ_BOOLEAN_VARIABLE)
                        .allowTransition(START, RELATIONAL_EXPRESSION, READ_BOOLEAN_VARIABLE)
                        .allowTransition(READ_BOOLEAN_VARIABLE, FINISH)
                        .allowTransition(RELATIONAL_EXPRESSION, FINISH)

                        .build();

        return new LogicalOperandMachine(matrix, exceptionThrower, factory);
    }

    private LogicalOperandMachine(TransitionMatrix<LogicalOperandStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower,
                                  ScriptElementExecutorFactory factory) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(READ_BOOLEAN_VARIABLE, new ReadBooleanVariableTransducer());
        registerTransducer(RELATIONAL_EXPRESSION, new ExecutorProgramElementTransducer(ScriptElement.RELATIONAL_EXPRESSION, factory));
    }
}
