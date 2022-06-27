package com.teamdev.calculator.fsm.calculator;

import com.teamdev.calculator.ResolvingException;
import com.teamdev.calculator.fsm.expression.ExpressionMachine;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.*;

import java.util.function.BiConsumer;

/**
 * {@code CalculatorMachine} is a realisation of {@link FiniteStateMachine}
 * that used to launch a {@link ExpressionMachine}.
 * */

public final class CalculatorMachine extends FiniteStateMachine<CalculatorStates, ShuntingYard, ResolvingException> {

    public static CalculatorMachine create(MathElementResolverFactory factory,
                                                                    ExceptionThrower<ResolvingException> exceptionThrower) {
        TransitionMatrix<CalculatorStates> matrix =
                TransitionMatrix.<CalculatorStates>builder()
                        .withStartState(CalculatorStates.START)
                        .withFinishState(CalculatorStates.FINISH)
                        .allowTransition(CalculatorStates.START, CalculatorStates.EXPRESSION)
                        .allowTransition(CalculatorStates.EXPRESSION, CalculatorStates.FINISH).build();

        return new CalculatorMachine(matrix, factory, exceptionThrower);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorStates> matrix, MathElementResolverFactory factory, ExceptionThrower<ResolvingException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        registerTransducer(CalculatorStates.START, Transducer.illegalTransition());
        registerTransducer(CalculatorStates.EXPRESSION, new DetachedShuntingYardTransducer<>(MathElement.EXPRESSION, consumer, factory));
        registerTransducer(CalculatorStates.FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}