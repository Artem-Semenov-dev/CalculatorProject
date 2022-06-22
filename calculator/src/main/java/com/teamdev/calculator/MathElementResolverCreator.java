package com.teamdev.calculator;

import com.teamdev.calculator.math.MathElementResolver;

@FunctionalInterface
public interface MathElementResolverCreator {

    MathElementResolver create();
}
