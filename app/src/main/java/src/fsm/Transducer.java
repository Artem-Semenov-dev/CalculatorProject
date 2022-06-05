package src.fsm;

@FunctionalInterface
public interface Transducer<O> {

    boolean doTransition(InputChain inputChain, O outputChain) throws DeadlockException;

    static <O> Transducer<O> autoTransition(){

        return (inputChain, outputChain) -> true;
    }

    static <O> Transducer<O> illegalTransition(){

        return (inputChain, outputChain) -> {

            throw new IllegalStateException("Transition to START make no sense");
        };
    }


}
