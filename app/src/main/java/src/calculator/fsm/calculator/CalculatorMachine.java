package src.calculator.fsm.calculator;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.expression.ShuntingYardStack;

public final class CalculatorMachine extends FiniteStateMachine<CalculatorState, ShuntingYardStack> {

    public static CalculatorMachine create() {
        TransitionMatrix<CalculatorState> matrix =
                TransitionMatrix.<CalculatorState>builder()
                        .withStartState(CalculatorState.START)
                        .withFinishState(CalculatorState.FINISH)
                        .allowTransition(CalculatorState.START, CalculatorState.EXPRESSION)
                        .allowTransition(CalculatorState.EXPRESSION, CalculatorState.FINISH).build();

        return new CalculatorMachine(matrix);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorState> matrix) {

        super(matrix);
    }
}
