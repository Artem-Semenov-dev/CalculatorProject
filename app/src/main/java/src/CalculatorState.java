package src;

import com.google.common.base.Preconditions;
import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;
import src.fsm.expression.ShuntingYardStack;

enum CalculatorState implements Transducer<ShuntingYardStack> {

    START(Transducer.illegalTransition()),
    EXPRESSION(new ExpressionTransducer()),
    FINISH((inputChain, outputChain) -> !inputChain.hasNext());

    private final Transducer<ShuntingYardStack> origin;

    CalculatorState(Transducer<ShuntingYardStack> origin) {
        this.origin = Preconditions.checkNotNull(origin);
    }


    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) throws DeadlockException {
        return origin.doTransition(inputChain, outputChain);
    }
}
