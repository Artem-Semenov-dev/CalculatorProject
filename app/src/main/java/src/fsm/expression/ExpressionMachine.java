package src.fsm.expression;

import src.fsm.FiniteStateMachine;
import src.fsm.TransitionMatrix;

import static src.fsm.expression.ExpressionStates.*;

public class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYardStack> {

    public static ExpressionMachine create() {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NUMBER)
                .allowTransition(NUMBER, BINARY_OPERATOR, FINISH)
                .allowTransition(BINARY_OPERATOR, NUMBER)

                .build();

        return new ExpressionMachine(matrix);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix) {
        super(matrix);
    }
}
