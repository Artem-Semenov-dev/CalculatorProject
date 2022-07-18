package com.teamdev.bazascript.interpreter.datastructure;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.type.Value;

import java.util.Optional;
import java.util.Set;


/**
 * {@code DataStructureExpressionTransducer} is an implementation of {@link Transducer}
 * that produce a filed value in result of execution expression to {@link DataStructureContext}.
 */

class DataStructureExpressionTransducer implements Transducer<DataStructureContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    DataStructureExpressionTransducer(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, DataStructureContext outputChain) throws ExecutionException {

        ScriptElementExecutor executor = factory.create(ScriptElement.EXPRESSION);

        Optional<Set<String>> structureFieldNames = Optional.ofNullable(outputChain.getScriptContext().memory()
                .getTemplate(outputChain.getStructureName()));

        if (structureFieldNames.isEmpty()){
            return false;
        }

        if (executor.execute(inputChain, outputChain.getScriptContext())){

            Value fieldValue = outputChain.getScriptContext().systemStack().current().result();

            if (outputChain.isParseOnly()){
                return true;
            }

            for (String name: structureFieldNames.get()){

                if (!outputChain.getFields().containsKey(name)){

                    outputChain.addFieldValue(name, fieldValue);

                    return true;
                }
            }

        }

        return false;
    }
}
