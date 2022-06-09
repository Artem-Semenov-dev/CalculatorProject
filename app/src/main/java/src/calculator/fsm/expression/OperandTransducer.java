package src.calculator.fsm.expression;

import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.operand.OperandMachine;
import src.calculator.math.CharSequenceReader;

public class OperandTransducer implements Transducer<ShuntingYardStack> {
    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) throws DeadlockException {

        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        OperandMachine operandMachine = OperandMachine.create();

        if (operandMachine.run(inputChain, nestingShuntingYardStack)) {

            outputChain.pushOperand(nestingShuntingYardStack.peekResult());

            return true;
        }

        return false;
    }
}
