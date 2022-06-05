package src;

import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;
import src.fsm.expression.ExpressionMachine;
import src.fsm.expression.ShuntingYardStack;

public class ExpressionTransducer implements Transducer<ShuntingYardStack> {


    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) throws DeadlockException {

        return ExpressionMachine.create().run(inputChain, outputChain);
    }
}
