package src;

import src.fsm.FiniteStateMachine;
import src.fsm.TransitionMatrix;
import src.fsm.expression.ShuntingYardStack;

import static src.CalculatorState.*;

final class CalculatorMachine extends FiniteStateMachine<CalculatorState, ShuntingYardStack> {

    static CalculatorMachine create() {
        TransitionMatrix<CalculatorState> matrix =
        TransitionMatrix.<CalculatorState>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH).build();

        return new CalculatorMachine(matrix);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorState> matrix) {

        super(matrix);
    }
}
