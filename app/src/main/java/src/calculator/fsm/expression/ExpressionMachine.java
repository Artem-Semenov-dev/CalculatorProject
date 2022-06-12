package src.calculator.fsm.expression;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.calculator.DetachedShuntingYardTransducer;
import src.calculator.fsm.util.ShuntingYardStack;
import src.calculator.math.MathElement;
import src.calculator.math.MathElementResolverFactory;

import static src.calculator.fsm.expression.ExpressionStates.*;

public final class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYardStack> {

    public static ExpressionMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, OPERAND)
                .allowTransition(OPERAND, BINARY_OPERATOR, FINISH)
                .allowTransition(BINARY_OPERATOR, OPERAND)

                .build();

        return new ExpressionMachine(matrix, factory);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix, MathElementResolverFactory factory) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(OPERAND, new DetachedShuntingYardTransducer(factory.create(MathElement.OPERAND)));
        registerTransducer(BINARY_OPERATOR, new BinaryOperatorTransducer());
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}


