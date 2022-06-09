package src.calculator.fsm.expression;

import src.calculator.fsm.FiniteStateMachine;
import src.calculator.fsm.TransitionMatrix;
import src.calculator.fsm.Transducer;

import static src.calculator.fsm.expression.ExpressionStates.*;

public final class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYardStack> {

    public static ExpressionMachine create() {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, OPERAND)
                .allowTransition(OPERAND, BINARY_OPERATOR, FINISH)
                .allowTransition(BINARY_OPERATOR, OPERAND)

                .build();

        return new ExpressionMachine(matrix);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(OPERAND, new OperandTransducer());
        registerTransducer(BINARY_OPERATOR, new BinaryOperatorTransducer());
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}

//    START(Transducer.illegalTransition()),
//        OPERAND((inputChain, outputChain) -> {
//
//        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();
//
//        Transducer<ShuntingYardStack> operandMachine = OperandMachine.create();
//
//        if (operandMachine.doTransition(inputChain, nestingShuntingYardStack)) {
//
//        outputChain.pushOperand(nestingShuntingYardStack.peekResult());
//
//        return true;
//        }
//
//        return false;
//        }),
//        BINARY_OPERATOR(new BinaryOperatorTransducer()),
//        FINISH(Transducer.autoTransition());
//
//private final Transducer<ShuntingYardStack> origin;
