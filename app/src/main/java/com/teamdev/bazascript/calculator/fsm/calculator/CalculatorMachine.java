package com.teamdev.bazascript.calculator.fsm.calculator;

import com.teamdev.bazascript.FiniteStateMachine;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.TransitionMatrix;
import com.teamdev.bazascript.calculator.fsm.expression.ExpressionMachine;
import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.calculator.math.MathElement;
import com.teamdev.bazascript.calculator.math.MathElementResolverFactory;

public final class CalculatorMachine extends FiniteStateMachine<CalculatorStates, ShuntingYardStack> {

    /**
     * {@code CalculatorMachine} is a realisation of {@link FiniteStateMachine}
     * that used to launch a {@link ExpressionMachine}.
     * */

    public static CalculatorMachine create(MathElementResolverFactory factory) {
        TransitionMatrix<CalculatorStates> matrix =
                TransitionMatrix.<CalculatorStates>builder()
                        .withStartState(CalculatorStates.START)
                        .withFinishState(CalculatorStates.FINISH)
                        .allowTransition(CalculatorStates.START, CalculatorStates.EXPRESSION)
                        .allowTransition(CalculatorStates.EXPRESSION, CalculatorStates.FINISH).build();

        return new CalculatorMachine(matrix, factory);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(CalculatorStates.START, Transducer.illegalTransition());
        registerTransducer(CalculatorStates.EXPRESSION, new DetachedShuntingYardTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(CalculatorStates.FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}