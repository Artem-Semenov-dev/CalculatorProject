package src.fsm.expression;

import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;

import static src.fsm.expression.ExpressionStates.*;

public class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYardStack> {

    public static Transducer<ShuntingYardStack> create() {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, OPERAND)
                .allowTransition(OPERAND, BINARY_OPERATOR, FINISH)
                .allowTransition(BINARY_OPERATOR, OPERAND)

                .build();

        return new ExpressionMachine(matrix);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix) {
        super(matrix);
    }
}