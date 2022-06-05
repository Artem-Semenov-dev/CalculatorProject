package src.fsm.expression;

import com.google.common.base.Preconditions;
import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;

public enum ExpressionStates implements Transducer<ShuntingYardStack> {

    START(Transducer.illegalTransition()),
    NUMBER(new NumberTransducer()),
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
