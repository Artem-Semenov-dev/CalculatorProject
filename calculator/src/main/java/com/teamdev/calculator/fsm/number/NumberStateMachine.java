package com.teamdev.calculator.fsm.number;

import com.google.common.base.Preconditions;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.TransitionMatrix;

import static com.teamdev.calculator.fsm.number.NumberStates.*;

/**
 * {@code NumberStateMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a number.
 */

public final class NumberStateMachine extends FiniteStateMachine<NumberStates, StringBuilder> {

    public static NumberStateMachine create() {
        TransitionMatrix<NumberStates> matrix = TransitionMatrix.<NumberStates>builder().
                withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(INTEGER_DIGIT, INTEGER_DIGIT, DOT, FINISH)
                .allowTransition(DOT, FLOATING_INTEGER)
                .allowTransition(FLOATING_INTEGER, FLOATING_INTEGER, FINISH)
                .build();

        return new NumberStateMachine(matrix);
    }

    private NumberStateMachine(TransitionMatrix<NumberStates> matrix) {

        super(Preconditions.checkNotNull(matrix));

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NEGATIVE_SIGN, new SymbolTransducer('-'));
        registerTransducer(INTEGER_DIGIT, new SymbolTransducer(Character::isDigit));
        registerTransducer(DOT, new SymbolTransducer('.'));
        registerTransducer(FLOATING_INTEGER, new SymbolTransducer(Character::isDigit));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

}

