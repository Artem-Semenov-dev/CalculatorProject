package src.calculator.fsm.calculator;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.brackets.ExpressionTransducer;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.math.MathElement;
import src.calculator.math.MathElementResolverFactory;

import static src.calculator.fsm.calculator.CalculatorState.*;

public final class CalculatorMachine extends FiniteStateMachine<CalculatorState, ShuntingYardStack> {

    public static CalculatorMachine create(MathElementResolverFactory factory) {
        TransitionMatrix<CalculatorState> matrix =
                TransitionMatrix.<CalculatorState>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, EXPRESSION)
                        .allowTransition(EXPRESSION, FINISH).build();

        return new CalculatorMachine(matrix, factory);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorState> matrix, MathElementResolverFactory factory) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(EXPRESSION, new ExpressionTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}