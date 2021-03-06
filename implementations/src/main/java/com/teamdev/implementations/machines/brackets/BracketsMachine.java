package com.teamdev.implementations.machines.brackets;

import com.google.common.base.Preconditions;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;
import com.teamdev.implementations.datastructures.ShuntingYard;
import com.teamdev.implementations.type.Value;

import java.util.function.BiConsumer;

/**
 * {@code BracketsMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing an expression inside the brackets.
 */

public final class BracketsMachine<O, E extends Exception> extends FiniteStateMachine<BracketsStates, O, E> {

    public static <O, E extends Exception> BracketsMachine<O, E> create(Transducer<O, E> transducer, ExceptionThrower<E> exceptionThrower) {

        Preconditions.checkNotNull(transducer);

        TransitionMatrix<BracketsStates> matrix = TransitionMatrix.<BracketsStates>builder()
                .withStartState(BracketsStates.START)
                .withFinishState(BracketsStates.FINISH)
                .allowTransition(BracketsStates.START, BracketsStates.OPENING_BRACKET)
                .allowTransition(BracketsStates.OPENING_BRACKET, BracketsStates.EXPRESSION)
                .allowTransition(BracketsStates.EXPRESSION, BracketsStates.CLOSING_BRACKET)
                .allowTransition(BracketsStates.CLOSING_BRACKET, BracketsStates.FINISH)
                .build();

        return new BracketsMachine<>(matrix, transducer, exceptionThrower);
    }

    private BracketsMachine(TransitionMatrix<BracketsStates> matrix, Transducer<O, E> transducer, ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        BiConsumer<ShuntingYard, Value> consumer = ShuntingYard::pushOperand;

        registerTransducer(BracketsStates.START, Transducer.illegalTransition());
        registerTransducer(BracketsStates.OPENING_BRACKET, Transducer.checkAndPassChar('(') );
        registerTransducer(BracketsStates.EXPRESSION, transducer);
        registerTransducer(BracketsStates.CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(BracketsStates.FINISH, Transducer.autoTransition());
    }
}
//new DetachedShuntingYardTransducer<>(MathElement.EXPRESSION, consumer, factory)
