package com.teamdev.bazascript.interpreter.raeddatastructure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.DataStructureValueVisitor;
import com.teamdev.implementations.type.Value;

import java.util.Map;

/**
 * {@code DataStructureFieldTransducer} is an implementation of {@link Transducer}
 * that produce value of data structure field to {@link ScriptContext}.
 */

class DataStructureFieldTransducer implements Transducer<ScriptContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        Value dataStructure = outputChain.systemStack().current().result();

        Map<String, Value> fields = DataStructureValueVisitor.read(dataStructure);

        StringBuilder fieldName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        if (nameMachine.run(inputChain, fieldName)){

            if (outputChain.isParseOnly()){
                return true;
            }

            if (fields.containsKey(fieldName.toString())){

                outputChain.systemStack().current().pushOperand(fields.get(fieldName.toString()));

                return true;
            }
            else throw new ExecutionException("No such field in data structure");
        }

        return false;
    }
}
