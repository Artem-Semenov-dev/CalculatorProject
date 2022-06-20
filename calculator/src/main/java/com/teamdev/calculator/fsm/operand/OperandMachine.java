package com.teamdev.calculator.fsm.operand;

import com.teamdev.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.calculator.fsm.function.FunctionTransducer;
import com.teamdev.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * {@code OperandMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing an operand. How an operand can act a number, an expression in brackets or function.
 */

public final class OperandMachine extends FiniteStateMachine<Object, ShuntingYardStack> {

    public static FiniteStateMachine<Object, ShuntingYardStack> create(MathElementResolverFactory factory) {

        BiConsumer<ShuntingYardStack, Double> consumer = ShuntingYardStack::pushOperand;

        return FiniteStateMachine.oneOfMachine(new NumberTransducer(factory.create(MathElement.NUMBER)),
                new DetachedShuntingYardTransducer<>(factory.create(MathElement.BRACKETS), consumer),
                new FunctionTransducer(factory.create(MathElement.FUNCTION)));
    }

    private OperandMachine(TransitionMatrix<Object> matrix, MathElementResolverFactory factory) {
        super(matrix, true);
    }
}
