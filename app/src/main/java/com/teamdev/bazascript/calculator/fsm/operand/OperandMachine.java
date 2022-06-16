package com.teamdev.bazascript.calculator.fsm.operand;

import com.teamdev.bazascript.FiniteStateMachine;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.TransitionMatrix;
import com.teamdev.bazascript.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.bazascript.calculator.fsm.function.FunctionTransducer;
import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.calculator.math.MathElement;
import com.teamdev.bazascript.calculator.math.MathElementResolverFactory;

/**
 * {@code OperandMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing an operand. How an operand can act a number, an expression in brackets or function.
 */

public final class OperandMachine extends FiniteStateMachine<OperandStates, ShuntingYardStack> {

    public static OperandMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<OperandStates> matrix = TransitionMatrix.<OperandStates>builder()
                .withStartState(OperandStates.START)
                .withFinishState(OperandStates.FINISH)
                .allowTransition(OperandStates.START, OperandStates.NUMBER, OperandStates.BRACKETS, OperandStates.FUNCTION)
                .allowTransition(OperandStates.NUMBER, OperandStates.FINISH)
                .allowTransition(OperandStates.FUNCTION, OperandStates.FINISH)
                .allowTransition(OperandStates.BRACKETS, OperandStates.FINISH)
                .build();

        return new OperandMachine(matrix, factory);
    }

    private OperandMachine(TransitionMatrix<OperandStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(OperandStates.START, Transducer.illegalTransition());
        registerTransducer(OperandStates.NUMBER, new NumberTransducer(factory.create(MathElement.NUMBER)));
        registerTransducer(OperandStates.BRACKETS, new DetachedShuntingYardTransducer(factory.create(MathElement.BRACKETS)));
        registerTransducer(OperandStates.FUNCTION, new FunctionTransducer(factory.create(MathElement.FUNCTION)));
        registerTransducer(OperandStates.FINISH, Transducer.autoTransition());
    }
}