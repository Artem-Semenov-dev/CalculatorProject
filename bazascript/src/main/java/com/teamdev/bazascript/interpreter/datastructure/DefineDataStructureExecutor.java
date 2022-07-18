package com.teamdev.bazascript.interpreter.datastructure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.machines.function.FunctionMachine;
import com.teamdev.implementations.machines.function.NameTransducer;

/**
 * {@code DefineDataStructureExecutor} is an implementation of {@link ScriptElementExecutor}
 * that create and run defineStructureMachine that invaded to define templates of data structures.
 */

public class DefineDataStructureExecutor implements ScriptElementExecutor {
    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        FunctionMachine<DataStructureDefineContext, ExecutionException> defineStructureMachine = FunctionMachine.create(
                new NameTransducer<>(
                        DataStructureDefineContext::addFieldName, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                }),
                Transducer.checkAndPassChar('{'),
                Transducer.checkAndPassChar('}'),
                DataStructureDefineContext::setStructureName,
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                }
        );

        DataStructureDefineContext defineContext = new DataStructureDefineContext();

        if (defineStructureMachine.run(inputChain, defineContext)){

            if (output.isParseOnly()){
                return true;
            }

            output.memory().addStructureTemplate(defineContext.getStructureName(), defineContext.getFieldNames());

            return true;
        }

        return false;
    }
}
