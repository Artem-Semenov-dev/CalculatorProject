package com.teamdev.bazascript.interpreter.prefixoperator;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.Value;

public class UpdateVariableTransducer implements Transducer<UnaryPrefixOperatorContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, UnaryPrefixOperatorContext outputChain) throws ExecutionException {
        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        if (nameMachine.run(inputChain, variableName)) {

            if (outputChain.getScriptContext().hasVariable(variableName.toString())) {

                if (outputChain.isParseOnly()) {
                    return true;
                }

                Value variableValue = outputChain.getScriptContext().memory().getVariableValue(variableName.toString());

                outputChain.getScriptContext().memory().setVariable(variableName.toString(), outputChain.applyOperator(variableValue));

                outputChain.getScriptContext().systemStack().current().pushOperand(outputChain.applyOperator(variableValue));

                return true;

            } else
                throw new ExecutionException("Operation with not initialised variable " + variableName + " cannot be performed");
        }
        return false;
    }
}
