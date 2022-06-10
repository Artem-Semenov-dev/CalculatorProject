package src.calculator.fsm.expression;

import src.calculator.math.CharSequenceReader;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.Transducer;
import src.calculator.math.MathElementResolver;
import src.calculator.math.ResolvingException;

import java.util.Optional;

public class OperandTransducer implements Transducer<ShuntingYardStack> {

    MathElementResolver resolver;

    OperandTransducer(MathElementResolver resolver) {
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
