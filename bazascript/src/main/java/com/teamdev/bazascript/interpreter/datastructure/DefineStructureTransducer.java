package com.teamdev.bazascript.interpreter.datastructure;

import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ExceptionThrower;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.machines.function.FunctionMachine;
import com.teamdev.implementations.machines.function.FunctionNameTransducer;

import java.util.function.BiConsumer;

public class DefineStructureTransducer implements Transducer<DataStructureDefineContext, ExecutionException> {
    @Override
    public boolean doTransition(CharSequenceReader inputChain, DataStructureDefineContext outputChain) throws ExecutionException {

        FunctionMachine<DataStructureDefineContext, ExecutionException> defineStructureMachine = FunctionMachine.create(
                new FunctionNameTransducer<>(
                        DataStructureDefineContext::addFieldName, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                }),
                new BiConsumer<DataStructureDefineContext, String>() {
                    @Override
                    public void accept(DataStructureDefineContext dataStructureDefineContext, String s) {
                        dataStructureDefineContext.setStructureName(s);
                    }
                },
                new ExceptionThrower<ExecutionException>() {
                    @Override
                    public void throwException(String errorMessage) throws ExecutionException {
                        throw new ExecutionException(errorMessage);
                    }
                }
        );

        return defineStructureMachine.run(inputChain, outputChain);
    }
}
