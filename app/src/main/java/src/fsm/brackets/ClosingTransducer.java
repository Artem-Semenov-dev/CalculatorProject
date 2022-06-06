package src.fsm.brackets;

import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;
import src.fsm.expression.ShuntingYardStack;

public class ClosingTransducer implements Transducer<ShuntingYardStack> {

    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) throws DeadlockException {
        return false;
    }
}
