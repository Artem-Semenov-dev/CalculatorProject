package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;

/**
 * {@code Interpreter} an API for interpreting program on BazaScript language. Program may contain:
 * - initializing of variable
 * - operations between variables
 * - function println(variable or expression) which will deducing result of program execution.
 *
 */

public class Interpreter {

    public ProgramResult interpret(BazaScriptProgram code){

        Preconditions.checkNotNull(code);

        InterpreterMachine interpreterMachine = new InterpreterMachine();

        interpreterMachine.run();

        return null;
    }
}
