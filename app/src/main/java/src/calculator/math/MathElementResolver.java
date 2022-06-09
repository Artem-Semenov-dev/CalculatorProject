package src.calculator.math;

import src.calculator.fsm.DeadlockException;

import java.util.Optional;

@FunctionalInterface
public interface MathElementResolver {

    Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException, DeadlockException;
}
