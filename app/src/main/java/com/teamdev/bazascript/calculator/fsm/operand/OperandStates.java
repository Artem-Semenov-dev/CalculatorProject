package com.teamdev.bazascript.calculator.fsm.operand;

/**
 * {@code OperandState} is an enumeration of states
 * that used in {@link OperandMachine}.
 */

public enum OperandStates {

    START,
    NUMBER,
    BRACKETS,
    FUNCTION,
    FINISH
}

