package src.calculator.fsm.operand;

import com.google.common.base.Preconditions;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.util.CharSequenceReader;
import src.calculator.fsm.util.ResolvingException;
import src.calculator.fsm.util.ShuntingYardStack;
import src.calculator.math.MathElementResolver;

import java.util.Optional;

/**
 * {@code NumberTransducer} is an implementation of {@link Transducer}
 * that produce a number as a result of reading input
 * to {@link ShuntingYardStack} output.
 */

public class NumberTransducer implements Transducer<ShuntingYardStack> {

    private final MathElementResolver resolver;

    NumberTransducer(MathElementResolver resolver) {
        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) throws ResolvingException {

        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){
            outputChain.pushOperand(resolve.get());

            return true;
        }
        return false;
    }

}
