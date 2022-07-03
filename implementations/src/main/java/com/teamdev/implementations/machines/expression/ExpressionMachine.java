package com.teamdev.implementations.machines.expression;

import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;
import com.teamdev.implementations.operators.AbstractBinaryOperator;

import java.util.function.BiConsumer;

/**
 * {@code ExpressionMachine} implementation of {@link FiniteStateMachine} which is intended to process
 * the general structure of math expression — operands and binary operators.
 * Operand may be a number, an expression in brackets, or a function —
 * see OperandMachine for details.
 */

public final class  ExpressionMachine<O, E extends Exception> extends FiniteStateMachine<ExpressionStates, O, E > {

    public static <O, E extends Exception> ExpressionMachine<O, E> create(BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                                                  Transducer<O, E> operandTransducer, ExceptionThrower<E> exceptionThrower) {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine<>(matrix, binaryConsumer, operandTransducer, exceptionThrower);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix,
                              BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                              Transducer<O, E> operandTransducer,
                              ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, operandTransducer);
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer<>(binaryConsumer));
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }
}
//new DetachedShuntingYardTransducer<O>(MathElement.OPERAND, consumer, factory)


