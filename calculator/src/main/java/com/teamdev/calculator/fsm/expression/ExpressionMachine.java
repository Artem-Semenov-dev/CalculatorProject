package com.teamdev.calculator.fsm.expression;

import com.teamdev.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.calculator.fsm.operand.OperandMachine;
import com.teamdev.calculator.fsm.util.PrioritizedBinaryOperator;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import java.util.function.BiConsumer;

/**
 * {@code ExpressionMachine} implementation of {@link FiniteStateMachine} which is intended to process
 * the general structure of math expression — operands and binary operators.
 * Operand may be a number, an expression in brackets, or a function —
 * see {@link OperandMachine} for details.
 */

public final class  ExpressionMachine<O> extends FiniteStateMachine<ExpressionStates, O> {

    public static <O> ExpressionMachine<O> create(BiConsumer<O, PrioritizedBinaryOperator> binaryConsumer,
                                                  Transducer<O> operandTransducer) {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine<>(matrix, binaryConsumer, operandTransducer);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix,
                              BiConsumer<O, PrioritizedBinaryOperator> binaryConsumer,
                              Transducer<O> operandTransducer) {
        super(matrix, true);

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, operandTransducer);
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer<>(binaryConsumer));
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }
}
//new DetachedShuntingYardTransducer<O>(MathElement.OPERAND, consumer, factory)


