package com.teamdev.bazascript.interpreter;

import com.teamdev.calculator.WrongExpressionException;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InterpreterIntegrationTest {

    @Test
    public void initializeVariableTest() throws WrongExpressionException {

        Interpreter interpreter = new Interpreter();

        BazaScriptProgram program = new BazaScriptProgram("a=2; println(a);");

        ProgramResult programResult = interpreter.interpret(program);

        assertEquals("2", programResult.getValue());
    }
}
