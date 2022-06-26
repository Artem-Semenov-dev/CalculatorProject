package com.teamdev.calculator.fsm.brackets;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.calculator.math.MathElement;
import com.teamdev.calculator.math.MathElementResolverFactory;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import java.util.function.BiConsumer;

/**
 * {@code BracketsMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing an expression inside the brackets.
 */

public final class BracketsMachine<O> extends FiniteStateMachine<BracketsStates, O> {

    public static <O> BracketsMachine<O> create(Transducer<O> transducer) {

        Preconditions.checkNotNull(transducer);

        TransitionMatrix<BracketsStates> matrix = TransitionMatrix.<BracketsStates>builder()
                .withStartState(BracketsStates.START)
                .withFinishState(BracketsStates.FINISH)
                .allowTransition(BracketsStates.START, BracketsStates.OPENING_BRACKET)
                .allowTransition(BracketsStates.OPENING_BRACKET, BracketsStates.EXPRESSION)
                .allowTransition(BracketsStates.EXPRESSION, BracketsStates.CLOSING_BRACKET)
                .allowTransition(BracketsStates.CLOSING_BRACKET, BracketsStates.FINISH)
                .build();

        return new BracketsMachine<>(matrix, transducer);
    }

    private BracketsMachine(TransitionMatrix<BracketsStates> matrix, Transducer<O> transducer) {
        super(matrix, true);

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        registerTransducer(BracketsStates.START, Transducer.illegalTransition());
        registerTransducer(BracketsStates.OPENING_BRACKET, Transducer.checkAndPassChar('(') );
        registerTransducer(BracketsStates.EXPRESSION, transducer);
        registerTransducer(BracketsStates.CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(BracketsStates.FINISH, Transducer.autoTransition());
    }
}
//new DetachedShuntingYardTransducer<>(MathElement.EXPRESSION, consumer, factory)
