package com.teamdev.bazascript.calculator.math;

/**
 * {@code MathElementResolverFactory} is a functional interface that
 * represents a factory for creating {@link MathElementResolver}.
 */

@FunctionalInterface
public interface MathElementResolverFactory {

    MathElementResolver create(MathElement mathElement);
}
