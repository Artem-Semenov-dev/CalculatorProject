package com.teamdev.bazascript.interpreter.switchoperator;

public enum SwitchStates {

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
