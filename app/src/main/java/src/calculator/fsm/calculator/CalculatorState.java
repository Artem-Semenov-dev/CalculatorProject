package src.calculator.fsm.calculator;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.fsm.InputChain;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.expression.ExpressionMachine;

enum CalculatorState implements Transducer<ShuntingYardStack> {

    START(Transducer.illegalTransition()),
    EXPRESSION(ExpressionMachine.create()),
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
