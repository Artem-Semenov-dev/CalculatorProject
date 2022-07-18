package com.teamdev.bazascript.interpreter.raeddatastructure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.DataStructureValueVisitor;
import com.teamdev.implementations.type.Value;

public class DataStructureNameTransducer implements Transducer<ScriptContext, ExecutionException> {
    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        int starPosition = inputChain.position();

        if (nameMachine.run(inputChain, variableName)){

            if (outputChain.hasVariable(variableName.toString())) {

                if (outputChain.isParseOnly()) {
                    return true;
                }

                Value variable = outputChain.memory().getVariable(variableName.toString());

                if (DataStructureValueVisitor.isDataStructure(variable)) {

                    outputChain.systemStack().current().pushOperand(variable);
                    return true;
                }

                inputChain.setPosition(starPosition);

                return false;
            }
        }

        return false;
    }
}
