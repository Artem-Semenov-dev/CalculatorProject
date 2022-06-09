package src.calculator.fsm;

import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.math.CharSequenceReader;

@FunctionalInterface
public interface Transducer<O> {

    boolean doTransition(CharSequenceReader inputChain, O outputChain) throws DeadlockException;

    static <O> Transducer<O> autoTransition() {

        return (inputChain, outputChain) -> true;
    }

    static <O> Transducer<O> illegalTransition() {

        return (inputChain, outputChain) -> {

            throw new IllegalStateException("Transition to START make no sense");
        };
    }

    static <O> Transducer<O> checkAndPassChar(char character) {

        return (inputChain, outputChain) -> {
            if (inputChain.canRead() && inputChain.read() == character) {
                inputChain.incrementPosition();
                return true;
            }
            return false;
        };
    }

    static Transducer<ShuntingYardStack> machineOnNewShuntingYard(Transducer<ShuntingYardStack> finiteStateMachine) {

        return (inputChain, outputChain) -> {
            ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

            if (finiteStateMachine.doTransition(inputChain, nestingShuntingYardStack)) {

                outputChain.pushOperand(nestingShuntingYardStack.peekResult());

                return true;
            }

            return false;
        };
    }
}
