package src.calculator.fsm.brackets;

import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.expression.ExpressionMachine;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.fsm.operand.OperandMachine;
import src.calculator.math.CharSequenceReader;

public class ExpressionTransducer implements Transducer<ShuntingYardStack> {
    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) throws DeadlockException {

        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        ExpressionMachine expressionMachine = ExpressionMachine.create();

        if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

            outputChain.pushOperand(nestingShuntingYardStack.peekResult());

            return true;
        }

        return false;
    }
}
