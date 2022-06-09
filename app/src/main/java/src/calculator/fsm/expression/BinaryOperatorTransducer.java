package src.calculator.fsm.expression;

import com.google.common.base.Preconditions;
import src.calculator.fsm.InputChain;
import src.calculator.fsm.Transducer;

import java.util.Optional;

class BinaryOperatorTransducer implements Transducer<ShuntingYardStack> {

    private final BinaryOperatorFactory factory = new BinaryOperatorFactory();

    @Override
    public boolean doTransition(InputChain inputChain, ShuntingYardStack outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

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
