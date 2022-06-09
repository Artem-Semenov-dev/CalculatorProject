package src.calculator.fsm.expression;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.Transducer;

public class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYardStack> {

    public static Transducer<ShuntingYardStack> create() {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine(matrix);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix) {
        super(matrix);
    }
}
