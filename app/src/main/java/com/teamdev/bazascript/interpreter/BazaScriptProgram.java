package com.teamdev.bazascript.interpreter;

/**
 * {@code BazaScriptProgram} is a tiny type which is used to store code for BazaScript program.
 */

public class BazaScriptProgram {

    private final String value;

    public BazaScriptProgram(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
