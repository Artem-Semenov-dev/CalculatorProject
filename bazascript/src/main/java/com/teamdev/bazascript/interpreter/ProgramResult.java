package com.teamdev.bazascript.interpreter;

/**
 * {@code ProgramResult} is an interpretation result of BazaScript program.
 */

public class ProgramResult {

    private String value;

    ProgramResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
