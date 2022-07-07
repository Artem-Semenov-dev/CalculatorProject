package com.teamdev.bazascript.interpreter.initvar.ternary;

/**
 * {@code TernaryStates} is an enumeration of states
 * that used in TernaryOperatorMachine.
 */

public enum TernaryStates {
    START,
    RELATIONAL_EXPRESSION,
    QUESTION_MARK,
    EXPRESSION,
    COLON,
    FINISH
}
