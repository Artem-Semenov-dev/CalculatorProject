package src.calculator.math;

import src.calculator.fsm.util.CharSequenceReader;
import src.calculator.fsm.util.ResolvingException;

import java.util.Optional;

@FunctionalInterface
public interface MathElementResolver {

    Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException;
}
