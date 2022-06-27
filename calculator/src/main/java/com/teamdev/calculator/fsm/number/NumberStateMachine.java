package com.teamdev.calculator.fsm.number;

import com.google.common.base.Preconditions;
import com.teamdev.fsm.*;
import com.teamdev.fsm.identifier.SymbolTransducer;

import java.util.Optional;

import static com.teamdev.calculator.fsm.number.NumberStates.*;

/**
 * {@code NumberStateMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a number.
 */

public final class NumberStateMachine<E extends Exception> extends FiniteStateMachine<NumberStates, StringBuilder, E> {

    public static <E extends Exception> Optional<Double> execute(CharSequenceReader inputChain, ExceptionThrower<E> exceptionThrower) throws E {
        StringBuilder stringBuilder = new StringBuilder();

        NumberStateMachine<E> numberMachine = NumberStateMachine.create(exceptionThrower);

        if (numberMachine.run(inputChain, stringBuilder)) {

            return Optional.of(Double.parseDouble(stringBuilder.toString()));
        }

        return Optional.empty();
    }

    public static <E extends Exception> NumberStateMachine<E> create(ExceptionThrower<E> exceptionThrower) {
        TransitionMatrix<NumberStates> matrix = TransitionMatrix.<NumberStates>builder().
                withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(INTEGER_DIGIT, INTEGER_DIGIT, DOT, FINISH)
                .allowTransition(DOT, FLOATING_INTEGER)
                .allowTransition(FLOATING_INTEGER, FLOATING_INTEGER, FINISH)
                .build();

        return new NumberStateMachine<>(matrix, exceptionThrower);
    }

    private NumberStateMachine(TransitionMatrix<NumberStates> matrix, ExceptionThrower<E> exceptionThrower) {

        super(Preconditions.checkNotNull(matrix), exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NEGATIVE_SIGN, new SymbolTransducer<>('-'));
        registerTransducer(INTEGER_DIGIT, new SymbolTransducer<>(Character::isDigit));
        registerTransducer(DOT, new SymbolTransducer<>('.'));
        registerTransducer(FLOATING_INTEGER, new SymbolTransducer<>(Character::isDigit));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

}

