package src.fsm.brackets;

import com.google.common.base.Preconditions;
import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;
import src.fsm.expression.ExpressionMachine;
import src.fsm.expression.ShuntingYardStack;

public enum BracketsStates implements Transducer<ShuntingYardStack> {

    START(Transducer.illegalTransition()),
    OPENING_BRACKET(Transducer.checkAndPassChar('(')),
    EXPRESSION(Transducer.machineOnNewShuntingYard(ExpressionMachine.create())),
    CLOSING_BRACKET(Transducer.checkAndPassChar(')')),
    FINISH(Transducer.autoTransition());

    private final Transducer<ShuntingYardStack> origin;

    BracketsStates(Transducer<ShuntingYardStack> origin) {
        this.origin = Preconditions.checkNotNull(origin);
    }

    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) throws DeadlockException {
        return origin.doTransition(inputChain, outputChain);
    }
}
