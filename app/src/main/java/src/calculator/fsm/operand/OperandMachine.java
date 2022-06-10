package src.calculator.fsm.operand;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.math.MathElement;
import src.calculator.math.MathElementResolverFactory;

import static src.calculator.fsm.operand.OperandStates.*;


public final class OperandMachine extends FiniteStateMachine<OperandStates, ShuntingYardStack> {

    public static OperandMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<OperandStates> matrix = TransitionMatrix.<OperandStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NUMBER, BRACKETS)
                .allowTransition(NUMBER, FINISH)
                .allowTransition(BRACKETS, FINISH)
                .build();

        return new OperandMachine(matrix, factory);
    }

    private OperandMachine(TransitionMatrix<OperandStates> matrix, MathElementResolverFactory factory) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NUMBER, new NumberTransducer(factory.create(MathElement.NUMBER)));
        registerTransducer(BRACKETS, new BracketsTransducer(factory.create(MathElement.BRACKETS)));
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}