package src.calculator.fsm.calculator;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.brackets.ExpressionTransducer;
import src.calculator.fsm.expression.ExpressionMachine;
import src.calculator.fsm.expression.ShuntingYardStack;

import static src.calculator.fsm.calculator.CalculatorState.*;

public final class CalculatorMachine extends FiniteStateMachine<CalculatorState, ShuntingYardStack> {

    public static CalculatorMachine create() {
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

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(EXPRESSION, new ExpressionTransducer());
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}
//    START(Transducer.illegalTransition()),
//        EXPRESSION(ExpressionMachine.create()),
//        FINISH((inputChain, outputChain) -> !inputChain.canRead());