package src.fsm;

import src.fsm.brackets.BracketsMachine;
import src.fsm.expression.ShuntingYardStack;

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

    static <O> Transducer<O> checkAndPassChar(char character){

        return (inputChain, outputChain) -> {
            if (inputChain.hasNext() && inputChain.currentSymbol() == character){
                inputChain.next();
                return true;
            }
            return false;
        };
    }

    static Transducer<ShuntingYardStack> machineOnNewShuntingYard(Transducer<ShuntingYardStack> finiteStateMachine){

        return (inputChain, outputChain) -> {
            ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

            if (finiteStateMachine.doTransition(inputChain, nestingShuntingYardStack)){

                outputChain.pushOperand(nestingShuntingYardStack.popResult());

                return true;
            }

            return false;
        };
    }
}
