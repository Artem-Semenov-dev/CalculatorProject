package src.calculator.fsm.brackets;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.expression.ShuntingYardStack;

public class BracketsMachine extends FiniteStateMachine<BracketsStates, ShuntingYardStack> {

    public static Transducer<ShuntingYardStack> create() {

        TransitionMatrix<BracketsStates> matrix = TransitionMatrix.<BracketsStates>builder()
                .withStartState(BracketsStates.START)
                .withFinishState(BracketsStates.FINISH)
                .allowTransition(BracketsStates.START, BracketsStates.OPENING_BRACKET)
                .allowTransition(BracketsStates.OPENING_BRACKET, BracketsStates.EXPRESSION)
                .allowTransition(BracketsStates.EXPRESSION, BracketsStates.CLOSING_BRACKET)
                .allowTransition(BracketsStates.CLOSING_BRACKET, BracketsStates.FINISH)
                .build();

        return new BracketsMachine(matrix);
    }

    protected BracketsMachine(TransitionMatrix<BracketsStates> matrix) {
        super(matrix);
    }
}
