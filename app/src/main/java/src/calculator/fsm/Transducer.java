package src.calculator.fsm;

import src.calculator.fsm.util.CharSequenceReader;
import src.calculator.fsm.util.ResolvingException;

@FunctionalInterface
public interface Transducer<O> {

    boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ResolvingException;

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
