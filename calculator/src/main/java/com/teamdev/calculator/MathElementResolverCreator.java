package com.teamdev.calculator;

import com.teamdev.calculator.math.MathElementResolver;

/**
 * {@code MathElementResolverCreator} is a functional interface that can be used to
 * create {@link MathElementResolver}.
 */

@FunctionalInterface
public interface MathElementResolverCreator {

    MathElementResolver create();
}
