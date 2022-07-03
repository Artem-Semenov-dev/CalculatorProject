package com.teamdev.implementations.machines.expression;

/**
 * {@code ExpressionState} is an enumeration of states
 * that used in {@link ExpressionMachine}.
 */

public enum ExpressionStates {

    START,
    OPERAND,
    BINARY_OPERATOR,
    FINISH
}


