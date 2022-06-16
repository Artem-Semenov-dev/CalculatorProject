package com.teamdev.bazascript.calculator.fsm.expression;

import com.teamdev.bazascript.calculator.fsm.operand.OperandMachine;
import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.FiniteStateMachine;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.TransitionMatrix;
import com.teamdev.bazascript.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.bazascript.calculator.math.MathElement;
import com.teamdev.bazascript.calculator.math.MathElementResolverFactory;

/**
 * {@code ExpressionMachine} implementation of {@link FiniteStateMachine} which is intended to process
 * the general structure of math expression — operands and binary operators.
 * Operand may be a number, an expression in brackets, or a function —
 * see {@link OperandMachine} for details.
 */

public final class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYardStack> {

    public static ExpressionMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine(matrix, factory);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, new DetachedShuntingYardTransducer(factory.create(MathElement.OPERAND)));
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer());
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }
}


