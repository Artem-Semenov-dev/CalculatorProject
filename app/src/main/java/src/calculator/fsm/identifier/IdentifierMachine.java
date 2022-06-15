package src.calculator.fsm.identifier;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.number.SymbolTransducer;

import static src.calculator.fsm.identifier.IdentifierStates.*;

/**
 * {@code IdentifierMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a name of function.
 */

public final class IdentifierMachine extends FiniteStateMachine<IdentifierStates, StringBuilder> {

    public static IdentifierMachine create() {

        TransitionMatrix<IdentifierStates> matrix = TransitionMatrix.<IdentifierStates>builder()

                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, LETTER)
                .allowTransition(LETTER, LETTER, FINISH)

                .build();
        return new IdentifierMachine(matrix);
    }

    private IdentifierMachine(TransitionMatrix<IdentifierStates> matrix) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(LETTER, new SymbolTransducer(Character::isLetter));
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}
