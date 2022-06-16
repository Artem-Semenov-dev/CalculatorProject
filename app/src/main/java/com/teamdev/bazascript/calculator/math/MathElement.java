package com.teamdev.bazascript.calculator.math;

import com.teamdev.bazascript.calculator.MathElementResolverFactoryImpl;

/**
 *  {@code NumberState} is an enumeration of mathematical token
 *  that used in {@link MathElementResolverFactoryImpl}.
 */

public enum MathElement {

    EXPRESSION,
    NUMBER,
    OPERAND,
    BRACKETS,
    CALCULATOR,
    FUNCTION
}
