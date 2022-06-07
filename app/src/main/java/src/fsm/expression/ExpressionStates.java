package src.fsm.expression;

import com.google.common.base.Preconditions;
import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;
import src.fsm.operand.OperandMachine;

public enum ExpressionStates implements Transducer<ShuntingYardStack> {

    START(Transducer.illegalTransition()),
    OPERAND((inputChain, outputChain) -> {

        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        OperandMachine operandMachine = OperandMachine.create();

        if (operandMachine.doTransition(inputChain, nestingShuntingYardStack)) {

            outputChain.pushOperand(nestingShuntingYardStack.peekResult());

            return true;
        }

        return false;
    }),
    BINARY_OPERATOR(new BinaryOperatorTransducer()),
    FINISH(Transducer.autoTransition());

    private final Transducer<ShuntingYardStack> origin;

    ExpressionStates(Transducer<ShuntingYardStack> origin) {
        this.origin = Preconditions.checkNotNull(origin);
    }

    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) throws DeadlockException {

        return origin.doTransition(inputChain, outputChain);
    }

}
