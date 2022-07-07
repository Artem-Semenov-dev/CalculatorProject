package com.teamdev.bazascript.interpreter.initvar;

/**
 * {@code InitVarStates} is an enumeration of states that used in {@link InitVarMachine}
 */

public enum InitVarStates {

    START,
    ASSIGN,
    NAME,
    EXPRESSION,
    FINISH
}
