package src.calculator.fsm.brackets;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.expression.ExpressionMachine;
import src.calculator.fsm.expression.ShuntingYardStack;

import static src.calculator.fsm.brackets.BracketsStates.*;

public final class BracketsMachine extends FiniteStateMachine<BracketsStates, ShuntingYardStack> {

    public static BracketsMachine create() {

        TransitionMatrix<BracketsStates> matrix = TransitionMatrix.<BracketsStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, OPENING_BRACKET)
                .allowTransition(OPENING_BRACKET, EXPRESSION)
                .allowTransition(EXPRESSION, CLOSING_BRACKET)
                .allowTransition(CLOSING_BRACKET, FINISH)
                .build();

        return new BracketsMachine(matrix);
    }

    private BracketsMachine(TransitionMatrix<BracketsStates> matrix) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('(') );
        registerTransducer(EXPRESSION, new ExpressionTransducer());
        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}
//    START(Transducer.illegalTransition()),
//    OPENING_BRACKET(Transducer.checkAndPassChar('(')),
//    EXPRESSION(Transducer.machineOnNewShuntingYard(ExpressionMachine.create())),
//    CLOSING_BRACKET(Transducer.checkAndPassChar(')')),
//    FINISH(Transducer.autoTransition());
//
//    private final Transducer<ShuntingYardStack> origin;
//
//    BracketsStates(Transducer<ShuntingYardStack> origin) {
//        this.origin = Preconditions.checkNotNull(origin);
//    }