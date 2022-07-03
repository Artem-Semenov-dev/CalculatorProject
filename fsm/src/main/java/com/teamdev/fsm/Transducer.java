package com.teamdev.fsm;

/**
 * {@code Transducer} is a functional interface that can be used to
 * produce {@param <O>} output based on a given input
 * and potentially throws a generic exception.
 */

@FunctionalInterface
public interface Transducer<O, E extends Exception> {

    boolean doTransition(CharSequenceReader inputChain, O outputChain) throws E;

    default Transducer<O, E> named(String name) {
        return new Transducer<>() {
            @Override
            public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws E {
                return Transducer.this.doTransition(inputChain, outputChain);
            }

            @Override
            public String toString() {
                return name;
            }
        };
    }

    static <O, E extends Exception> Transducer<O, E> autoTransition() {

        return (inputChain, outputChain) -> true;
    }

    static <O, E extends Exception> Transducer<O, E> illegalTransition() {

        return (inputChain, outputChain) -> {

            throw new IllegalStateException("Transition to START make no sense");
        };
    }

    static <O, E extends Exception> Transducer<O, E> checkAndPassChar(char character) {

        return (inputChain, outputChain) -> {
            if (inputChain.canRead() && inputChain.read() == character) {
                inputChain.incrementPosition();
                return true;
            }
            return false;
        };
    }

    default Transducer<O, E> and(Transducer<O, E> transducer) {
        return (inputChain, outputChain) -> {

            if (doTransition(inputChain, outputChain)) {
                return transducer.doTransition(inputChain, outputChain);
            }

            return false;
        };
    }
}
