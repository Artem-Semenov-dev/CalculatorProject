package src.fsm.operand;

import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.fsm.expression.ShuntingYardStack;

import static src.fsm.operand.OperandStates.*;

public class OperandMachine extends FiniteStateMachine<OperandStates, ShuntingYardStack> {

    public static OperandMachine create(){

        TransitionMatrix<OperandStates> matrix= TransitionMatrix.<OperandStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NUMBER, BRACKETS)
                .allowTransition(NUMBER, FINISH)
                .allowTransition(BRACKETS, FINISH)
                .build();

        return new OperandMachine(matrix);
    }

    protected OperandMachine(TransitionMatrix<OperandStates> matrix) {
        super(matrix);
    }
}
