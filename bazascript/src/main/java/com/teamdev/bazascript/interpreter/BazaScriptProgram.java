package com.teamdev.bazascript.interpreter;

/**
 * {@code BazaScriptProgram} is a text of program on BazaScript programming language.
 */

public class BazaScriptProgram {

    private final String value;

    public BazaScriptProgram(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
