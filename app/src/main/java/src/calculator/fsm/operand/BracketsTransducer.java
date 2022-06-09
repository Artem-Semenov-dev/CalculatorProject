package src.calculator.fsm.operand;

import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.brackets.BracketsMachine;
import src.calculator.fsm.brackets.BracketsStates;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.math.CharSequenceReader;

public class BracketsTransducer implements Transducer<ShuntingYardStack> {
    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) throws DeadlockException {
        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        BracketsMachine bracketsMachine = BracketsMachine.create();

        if (bracketsMachine.run(inputChain, nestingShuntingYardStack)) {

            outputChain.pushOperand(nestingShuntingYardStack.peekResult());

            return true;
        }

        return false;
    }
}
