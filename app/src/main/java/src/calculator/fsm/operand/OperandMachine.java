package src.calculator.fsm.operand;

import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.expression.ShuntingYardStack;

public class OperandMachine extends FiniteStateMachine<OperandStates, ShuntingYardStack> {

    public static Transducer<ShuntingYardStack> create() {

        TransitionMatrix<OperandStates> matrix = TransitionMatrix.<OperandStates>builder()
                .withStartState(OperandStates.START)
                .withFinishState(OperandStates.FINISH)
                .allowTransition(OperandStates.START, OperandStates.NUMBER, OperandStates.BRACKETS)
                .allowTransition(OperandStates.NUMBER, OperandStates.FINISH)
                .allowTransition(OperandStates.BRACKETS, OperandStates.FINISH)
                .build();

        return new OperandMachine(matrix);
    }

    private OperandMachine(TransitionMatrix<OperandStates> matrix) {
        super(matrix);
    }
}
