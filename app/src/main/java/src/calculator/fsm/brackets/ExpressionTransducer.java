package src.calculator.fsm.brackets;

import src.calculator.math.CharSequenceReader;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.Transducer;
import src.calculator.fsm.expression.ShuntingYardStack;
import src.calculator.math.MathElementResolver;
import src.calculator.math.ResolvingException;

import java.util.Optional;

public class ExpressionTransducer implements Transducer<ShuntingYardStack> {

    MathElementResolver resolver;

    public ExpressionTransducer(MathElementResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYardStack outputChain) throws DeadlockException, ResolvingException {

        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){
            outputChain.pushOperand(resolve.get());

            return true;
        }
        return false;
    }
}