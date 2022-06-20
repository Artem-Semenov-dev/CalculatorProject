package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.WrongExpressionException;
import com.teamdev.bazascript.interpreter.execute.InterpreterMachine;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;

/**
 * {@code Interpreter} an API for interpreting program on BazaScript language. Program may contain:
 * - initializing of variable
 * - operations between variables
 * - function println(variable or expression) which will deducing result of program execution.
 *
 */

public class Interpreter {

    public ProgramResult interpret(BazaScriptProgram code) throws WrongExpressionException {

        Preconditions.checkNotNull(code);

        CharSequenceReader inputChain = new CharSequenceReader(code.getValue());

        VariableHolder outputChain = new VariableHolder();

        InterpreterMachine interpreterMachine = InterpreterMachine.create();

        try {
            if (!interpreterMachine.run(inputChain, outputChain)) {

                raiseException(inputChain);
            }
        } catch (ResolvingException | WrongExpressionException e) {
            raiseException(inputChain);
        }

        return new ProgramResult(outputChain.getVariableData());
    }

    private static void raiseException(CharSequenceReader inputChain) throws WrongExpressionException {
        throw new WrongExpressionException("Syntax error", inputChain.position());
    }
}
