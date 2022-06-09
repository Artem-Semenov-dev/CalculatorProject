package src.calculator.math;

import src.calculator.fsm.InputChain;

import java.util.Optional;

@FunctionalInterface
public interface MathElementResolver {

    Optional<Double> resolve(CharSequenceReader inputChain);
}
