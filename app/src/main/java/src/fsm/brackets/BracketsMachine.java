package src.fsm.brackets;

import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.fsm.expression.ShuntingYardStack;

import static src.fsm.brackets.BracketsStates.*;

public class BracketsMachine extends FiniteStateMachine<BracketsStates, ShuntingYardStack> {

    public static BracketsMachine create(){

        TransitionMatrix<BracketsStates> matrix= TransitionMatrix.<BracketsStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, OPENING_BRACKET)
                .allowTransition(OPENING_BRACKET, EXPRESSION)
                .allowTransition(EXPRESSION, CLOSING_BRACKET)
                .allowTransition(CLOSING_BRACKET, FINISH)
                .build();

        return new BracketsMachine(matrix);
    }

    protected BracketsMachine(TransitionMatrix<BracketsStates> matrix) {
        super(matrix);
    }
}
