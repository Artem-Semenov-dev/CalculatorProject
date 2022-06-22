package com.teamdev.bazascript.interpreter;

import com.teamdev.calculator.WrongExpressionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AbstractTest {

    private final Interpreter interpreter = new Interpreter();

    @ParameterizedTest
    @MethodSource("positiveSource")
    void testPositiveCases(String mathScript, String expected, String assertMsg) throws IncorrectProgramException {
        BazaScriptProgram script = new BazaScriptProgram(mathScript);
        ProgramResult output = interpreter.interpret(script);

        Assertions.assertEquals(expected, output.getValue(), assertMsg);
    }
}
