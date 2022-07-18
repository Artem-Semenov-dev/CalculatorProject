package com.teamdev.bazascript.interpreter.datastructure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.machines.function.FunctionMachine;
import com.teamdev.implementations.type.DataStructureValue;
import com.teamdev.implementations.type.Value;

/**
 * {@code FillDataStructureExecutor} is an implementation of {@link ScriptElementExecutor}
 * that create and run fillDataStructureMachine for fill template of data structure.
 */

public class FillDataStructureExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public FillDataStructureExecutor(ScriptElementExecutorFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        FunctionMachine<DataStructureContext, ExecutionException> fillDataStructureMachine = FunctionMachine.create(

                new DataStructureExpressionTransducer(factory),
                Transducer.checkAndPassChar('{'), Transducer.<DataStructureContext, ExecutionException>checkAndPassChar('}')
                        .and((input, outputChain) -> {

                            Value value = new DataStructureValue(outputChain.getFields());

                            if (!outputChain.isParseOnly()) {

                                outputChain.getScriptContext().systemStack().current().pushOperand(value);
                            }

                            return true;
                        }),
                DataStructureContext::setStructureName,
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                });

        DataStructureContext dataStructureContext = new DataStructureContext(output);

        return fillDataStructureMachine.run(inputChain, dataStructureContext);
    }
}
