package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.Value;

public class ReadVariableExecutor implements ScriptElementExecutor {

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        if (nameMachine.run(inputChain, variableName)) {

            if (output.hasVariable(variableName.toString())) {

                if (output.isParseOnly()) {
                    return true;
                }

                Value variable = output.memory().getVariableValue(variableName.toString());

                output.systemStack().current().pushOperand(variable);

                return true;

            } else throw new ExecutionException("Not existing variable in memory " + variableName);
        }
        return false;
    }
}
