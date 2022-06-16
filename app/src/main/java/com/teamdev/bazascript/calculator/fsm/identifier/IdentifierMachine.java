package com.teamdev.bazascript.calculator.fsm.identifier;

import com.teamdev.bazascript.FiniteStateMachine;
import com.teamdev.bazascript.Transducer;
import com.teamdev.bazascript.TransitionMatrix;
import com.teamdev.bazascript.calculator.fsm.number.SymbolTransducer;

/**
 * {@code IdentifierMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a name of function.
 */

public final class IdentifierMachine extends FiniteStateMachine<IdentifierStates, StringBuilder> {

    public static IdentifierMachine create() {

        TransitionMatrix<IdentifierStates> matrix = TransitionMatrix.<IdentifierStates>builder()

                .withStartState(IdentifierStates.START)
                .withFinishState(IdentifierStates.FINISH)
                .allowTransition(IdentifierStates.START, IdentifierStates.LETTER)
                .allowTransition(IdentifierStates.LETTER, IdentifierStates.LETTER, IdentifierStates.FINISH)

                .build();
        return new IdentifierMachine(matrix);
    }

    private IdentifierMachine(TransitionMatrix<IdentifierStates> matrix) {
        super(matrix);

        registerTransducer(IdentifierStates.START, Transducer.illegalTransition());
        registerTransducer(IdentifierStates.LETTER, new SymbolTransducer(Character::isLetter));
        registerTransducer(IdentifierStates.FINISH, Transducer.autoTransition());
    }
}