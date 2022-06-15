package src.calculator.fsm.calculator;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.util.ShuntingYardStack;
import src.calculator.math.MathElement;
import src.calculator.math.MathElementResolverFactory;

import static src.calculator.fsm.calculator.CalculatorStates.*;

public final class CalculatorMachine extends FiniteStateMachine<CalculatorStates, ShuntingYardStack> {

    /**
     * {@code CalculatorMachine} is a realisation of {@link FiniteStateMachine}
     * that used to launch a {@link src.calculator.fsm.expression.ExpressionMachine}.
     * */

    public static CalculatorMachine create(MathElementResolverFactory factory) {
        TransitionMatrix<CalculatorStates> matrix =
                TransitionMatrix.<CalculatorStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, EXPRESSION)
                        .allowTransition(EXPRESSION, FINISH).build();

        return new CalculatorMachine(matrix, factory);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(EXPRESSION, new DetachedShuntingYardTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}