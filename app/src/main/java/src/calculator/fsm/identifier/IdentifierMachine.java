package src.calculator.fsm.identifier;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.number.SymbolTransducer;

import static src.calculator.fsm.identifier.IdentifierState.*;


public final class IdentifierMachine extends FiniteStateMachine<IdentifierState, StringBuilder> {

    public static IdentifierMachine create() {

        TransitionMatrix<IdentifierState> matrix = TransitionMatrix.<IdentifierState>builder()

                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, LETTER)
                .allowTransition(LETTER, LETTER, FINISH)

                .build();
        return new IdentifierMachine(matrix);
    }

    private IdentifierMachine(TransitionMatrix<IdentifierState> matrix) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(LETTER, new SymbolTransducer(Character::isLetter));
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}
