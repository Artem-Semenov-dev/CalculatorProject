package com.teamdev.calculator.fsm.operand;

import com.teamdev.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.TransitionMatrix;

import java.util.function.BiConsumer;

/**
 * {@code OperandMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing an operand. How an operand can act a number, an expression in brackets or function.
 */

public final class OperandMachine extends FiniteStateMachine<Object, ShuntingYard> {

    public static FiniteStateMachine<Object, ShuntingYard> create(MathElementResolverFactory factory) {

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        return FiniteStateMachine.oneOfMachine(new DetachedShuntingYardTransducer<>(MathElement.NUMBER, consumer, factory),
                new DetachedShuntingYardTransducer<>(MathElement.BRACKETS, consumer, factory),
                new DetachedShuntingYardTransducer<>(MathElement.FUNCTION, consumer, factory));
    }

    private OperandMachine(TransitionMatrix<Object> matrix, MathElementResolverFactory factory) {
        super(matrix, true);
    }
}

//new NumberTransducer(factory.create(MathElement.NUMBER)
