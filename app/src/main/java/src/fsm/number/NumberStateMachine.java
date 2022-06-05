package src.fsm.number;

import com.google.common.base.Preconditions;
import src.fsm.*;

import static src.fsm.number.NumberState.*;

public final class NumberStateMachine extends FiniteStateMachine<NumberState, StringBuilder> {

    public static NumberStateMachine create(){
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
