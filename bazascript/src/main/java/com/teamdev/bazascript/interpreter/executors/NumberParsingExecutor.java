package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.implementations.machines.number.NumberStateMachine;
import com.teamdev.implementations.type.Value;

import java.util.Optional;

public class NumberParsingExecutor implements ScriptElementExecutor {

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {
        Optional<Value> execute = NumberStateMachine.execute(inputChain, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        if (execute.isPresent()) {

            if (output.isParseOnly()) {
                return true;
            }

            output.systemStack().current().pushOperand(execute.get());

            return true;
        }

        return false;
    }
}
