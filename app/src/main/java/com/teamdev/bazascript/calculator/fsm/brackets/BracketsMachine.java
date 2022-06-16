package com.teamdev.bazascript.calculator.fsm.brackets;

import com.teamdev.bazascript.calculator.fsm.util.ShuntingYardStack;
import com.teamdev.bazascript.FiniteStateMachine;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.TransitionMatrix;
import com.teamdev.bazascript.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.bazascript.calculator.math.MathElement;
import com.teamdev.bazascript.calculator.math.MathElementResolverFactory;

/**
 * {@code BracketsMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing an expression inside the brackets.
 */

public final class BracketsMachine extends FiniteStateMachine<BracketsStates, ShuntingYardStack> {

    public static BracketsMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<BracketsStates> matrix = TransitionMatrix.<BracketsStates>builder()
                .withStartState(BracketsStates.START)
                .withFinishState(BracketsStates.FINISH)
                .allowTransition(BracketsStates.START, BracketsStates.OPENING_BRACKET)
                .allowTransition(BracketsStates.OPENING_BRACKET, BracketsStates.EXPRESSION)
                .allowTransition(BracketsStates.EXPRESSION, BracketsStates.CLOSING_BRACKET)
                .allowTransition(BracketsStates.CLOSING_BRACKET, BracketsStates.FINISH)
                .build();

        return new BracketsMachine(matrix, factory);
    }

    private BracketsMachine(TransitionMatrix<BracketsStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(BracketsStates.START, Transducer.illegalTransition());
        registerTransducer(BracketsStates.OPENING_BRACKET, Transducer.checkAndPassChar('(') );
        registerTransducer(BracketsStates.EXPRESSION, new DetachedShuntingYardTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(BracketsStates.CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(BracketsStates.FINISH, Transducer.autoTransition());
    }
}
