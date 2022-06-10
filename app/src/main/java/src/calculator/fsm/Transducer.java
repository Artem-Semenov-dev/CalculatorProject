package src.calculator.fsm;

import src.calculator.math.CharSequenceReader;
import src.calculator.math.ResolvingException;

@FunctionalInterface
public interface Transducer<O> {

    boolean doTransition(CharSequenceReader inputChain, O outputChain) throws DeadlockException, ResolvingException;

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
}
