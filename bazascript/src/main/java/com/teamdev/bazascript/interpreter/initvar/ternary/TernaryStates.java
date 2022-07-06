package com.teamdev.bazascript.interpreter.initvar.ternary;

/**
 * {@code TernaryStates} is an enumeration of states
 * that used in TernaryOperatorMachine.
 */

public enum TernaryStates {
    START,
    OPENING_BRACKET,
    RELATIONAL_EXPRESSION,
    CLOSING_BRACKET,
    QUESTION_MARK,
    EXPRESSION,
    DOUBLE_FLOW,
    FINISH
}
