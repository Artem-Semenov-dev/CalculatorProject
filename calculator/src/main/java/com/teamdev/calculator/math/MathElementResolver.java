package com.teamdev.calculator.math;

import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.calculator.ResolvingException;

import java.util.Optional;

/**
 * {@code MathElementResolver} is a functional interface that can be used to
 * implement any code that can resolve {@link CharSequenceReader} to {@link Double}
 * and throws {@link ResolvingException}.
 */

@FunctionalInterface
public interface MathElementResolver {

    Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException;
}
