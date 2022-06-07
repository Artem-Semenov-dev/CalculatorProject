package src.fsm.operand;

import com.google.common.base.Preconditions;
import src.fsm.DeadlockException;
import src.fsm.InputChain;
import src.fsm.Transducer;
import src.fsm.brackets.BracketsMachine;
import src.fsm.expression.ShuntingYardStack;

public enum OperandStates implements Transducer<ShuntingYardStack> {
    START(Transducer.illegalTransition()),
    NUMBER(new NumberTransducer()),
    BRACKETS((inputChain, outputChain) -> {
        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        BracketsMachine bracketsMachine = BracketsMachine.create();

        if (bracketsMachine.doTransition(inputChain, nestingShuntingYardStack)){

            outputChain.pushOperand(nestingShuntingYardStack.popResult());

            return true;
        }

        return false;
    }),
    FINISH(Transducer.autoTransition());

    private final Transducer<ShuntingYardStack> origin;

    OperandStates(Transducer<ShuntingYardStack> origin) {
        this.origin = Preconditions.checkNotNull(origin);
    }


    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) throws DeadlockException {
        return origin.doTransition(inputChain, outputChain);
    }
}
