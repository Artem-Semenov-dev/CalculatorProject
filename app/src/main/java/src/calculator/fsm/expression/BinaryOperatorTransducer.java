package src.calculator.fsm.expression;

import com.google.common.base.Preconditions;
import src.calculator.math.CharSequenceReader;
import src.calculator.fsm.Transducer;

import java.util.Optional;

class BinaryOperatorTransducer implements Transducer<ShuntingYardStack> {

    private final BinaryOperatorFactory factory = new BinaryOperatorFactory();

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        if (!inputChain.canRead()) {
            return false;
        }

        Optional<PrioritizedBinaryOperator> operator = factory.create(inputChain.read());

        if (operator.isPresent()) {
            outputChain.pushOperator(operator.get());

            inputChain.incrementPosition();

            return true;
        }
        return false;
    }
}
