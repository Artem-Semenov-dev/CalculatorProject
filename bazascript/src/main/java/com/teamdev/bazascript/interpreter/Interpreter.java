package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.execute.InterpreterMachine;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.calculator.ResolvingException;
import com.teamdev.fsm.ExceptionThrower;

/**
 * {@code Interpreter} an API for interpreting program on BazaScript language. Program may contain:
 * - initializing of variable
 * - operations between variables
 * - function println(variable or expression) which will deducing result of program execution.
 */

public class Interpreter {

    private static void raiseException(CharSequenceReader inputChain) throws IncorrectProgramException {
        throw new IncorrectProgramException("Syntax error", inputChain.position());
    }

    public ProgramResult interpret(BazaScriptProgram code) throws IncorrectProgramException {

        Preconditions.checkNotNull(code);

        CharSequenceReader inputChain = new CharSequenceReader(code.getValue());

        ScriptContext scriptContext = new ScriptContext();

        ScriptElementExecutorFactory factory = new ScriptElementExecutorFactoryImpl();

        InterpreterMachine interpreterMachine = InterpreterMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        try {
            if (!interpreterMachine.run(inputChain, scriptContext)) {

                raiseException(inputChain);
            }
        } catch (IncorrectProgramException | ExecutionException e) {
            raiseException(inputChain);
        }

        return new ProgramResult(scriptContext.getOutput().content());
    }
}
