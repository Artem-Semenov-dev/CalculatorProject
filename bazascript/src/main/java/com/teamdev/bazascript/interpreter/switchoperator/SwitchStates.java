package com.teamdev.bazascript.interpreter.switchoperator;

/**
 * {@code SwitchStates} is an enumeration of states that used in {@link SwitchOperatorMachine}.
 */

enum SwitchStates {

    START,
    FINISH,
    SWITCH_KEYWORD,
    OPENING_BRACE,
    VARIABLE,
    CASE,
    OPTION,
    COLON,
    STATEMENT_LIST,
    DEFAULT,
    CLOSING_BRACE
}
