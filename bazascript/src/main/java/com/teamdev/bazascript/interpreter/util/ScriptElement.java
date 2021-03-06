package com.teamdev.bazascript.interpreter.util;

/**
 * {@code ScriptElement} is an enumeration of program elements
 * that can be used in {@link ScriptElementExecutorFactory}.
 */

public enum ScriptElement {
    NUMBER,
    NUMERIC_EXPRESSION,
    RELATIONAL_EXPRESSION,
    EXPRESSION,
    OPERAND,
    BRACKETS,
    FUNCTION,
    STATEMENT,
    INIT_VAR,
    PROGRAM,
    PROCEDURE,
    READ_VARIABLE,
    WHILE_OPERATOR,
    TERNARY_OPERATOR
}
