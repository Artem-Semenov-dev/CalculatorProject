package com.teamdev.bazascript.interpreter.variable;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.UnaryPrefixOperatorContext;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.Value;

public class ProduceVariableTransducer implements Transducer<UnaryPrefixOperatorContext, ExecutionException> {
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

                Value variableValue = outputChain.getScriptContext().memory().getVariableValueFromCache(variableName.toString());

                if (outputChain.readVariableOnly()) {

                    outputChain.getScriptContext().systemStack().current().pushOperand(variableValue);
                } else {

                    Value variableUpdateResult = outputChain.applyOperator(variableValue);

                    outputChain.getScriptContext().memory().setVariableToCache(variableName.toString(), variableUpdateResult);

                    outputChain.getScriptContext().systemStack().current().pushOperand(variableUpdateResult);
                }

                return true;

            } else
                throw new ExecutionException("Operation with not initialised variable " + variableName + " cannot be performed");
        }
        return false;
    }
}
