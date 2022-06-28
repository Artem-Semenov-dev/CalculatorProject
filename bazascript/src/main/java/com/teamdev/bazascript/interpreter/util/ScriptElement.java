package com.teamdev.bazascript.interpreter.util;

/**
 * {@code ScriptElement} is an enumeration of program elements
 * that can be used in {@link ScriptElementExecutorFactory}.
 */

public enum ScriptElement {
    NUMBER,
    EXPRESSION,
    OPERAND,
    BRACKETS,
    FUNCTION,
    STATEMENT,
    INITVAR,
    PROGRAM,
    PROCEDURE,
    READVARIABLE
}
