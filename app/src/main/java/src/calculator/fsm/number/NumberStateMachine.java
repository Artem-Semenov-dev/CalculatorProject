package src.calculator.fsm.number;

import com.google.common.base.Preconditions;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;

import static src.calculator.fsm.number.NumberState.*;

public final class NumberStateMachine extends FiniteStateMachine<NumberState, StringBuilder> {

    public static Transducer<StringBuilder> create() {
        TransitionMatrix<NumberState> matrix = TransitionMatrix.<NumberState>builder().
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

    private NumberStateMachine(TransitionMatrix<NumberState> matrix) {

        super(Preconditions.checkNotNull(matrix));
    }

}
