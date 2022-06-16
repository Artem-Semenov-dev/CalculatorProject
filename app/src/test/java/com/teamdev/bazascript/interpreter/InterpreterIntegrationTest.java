package com.teamdev.bazascript.interpreter;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterIntegrationTest {

    @Test
    public void initializeVariableTest() {

        BazaScriptProgram program = new BazaScriptProgram("a=2; println(a);");

        InterpreterMachine interpreterMachine = new InterpreterMachine();

        interpreterMachine.run();

        VariableHolder variableHolder = new VariableHolder();

        ProgramResult programResult = new ProgramResult(variableHolder.getVariableData());

        assertEquals("2", programResult.getValue());
    }
}
