package com.teamdev.bazascript.interpreter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AbstractTest {

    private final Interpreter interpreter = new Interpreter();

    @ParameterizedTest
    @MethodSource("positiveSource")
    void testPositiveCases(String programCode, String expected, String assertMsg) throws IncorrectProgramException {
        BazaScriptProgram script = new BazaScriptProgram(programCode);
        ProgramResult output = interpreter.interpret(script);

        Assertions.assertEquals(expected, output.getValue(), assertMsg);
    }

    @ParameterizedTest
    @MethodSource("negativeCases")
    void negativeCase(String programCode, int expectedErrorPosition, String errorMessage) {

        BazaScriptProgram script = new BazaScriptProgram(programCode);

        IncorrectProgramException programException =
                Assertions.assertThrows(IncorrectProgramException.class,
                        () -> interpreter.interpret(script));

        assertEquals(expectedErrorPosition, programException.getErrorPosition(), errorMessage);
    }
}
