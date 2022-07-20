package com.teamdev.bazascript.interpreter.forloop;

public enum ForLoopStates {

    START,
    FINISH,
    FOR_KEYWORD,
    OPENING_BRACKET,
    CLOSING_BRACKET,
    INITIALISE_STATEMENT,
    CONDITION_STATEMENT,
    UPDATE_VARIABLE_STATEMENT,
    LIST_OF_STATEMENTS,
    FIRST_SEPARATOR,
    SECOND_SEPARATOR
}
