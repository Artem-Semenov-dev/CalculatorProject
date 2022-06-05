package src.fsm.expression;

import src.fsm.InputChain;
import src.fsm.Transducer;

import java.util.Optional;

class BinaryOperatorTransducer implements Transducer<ShuntingYardStack> {

    private final BinaryOperatorFactory factory = new BinaryOperatorFactory();

    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) {

        if (!inputChain.hasNext()) {
            return false;
        }

        Optional<PrioritizedBinaryOperator> operator = factory.create(inputChain.currentSymbol());

        if (operator.isPresent()) {
            outputChain.pushOperator(operator.get());

            inputChain.next();

            return true;
        }
        return false;
    }
}
