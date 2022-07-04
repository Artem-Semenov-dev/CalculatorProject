package com.teamdev.bazascript.interpreter.executors;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.FunctionHolderWithContext;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.implementations.machines.function.FunctionFactory;
import com.teamdev.implementations.type.Value;

public class FunctionFactoryExecutor<I> implements ScriptElementExecutor {
    private final FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine;

    public FunctionFactoryExecutor(FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, output);

        FunctionFactory functionFactory = new FunctionFactory();

        var functionHolder = new FunctionHolderWithContext(output);

        if (machine.run(inputChain, functionHolder)) {

            if(output.isParseonly()){
                return true;
            }

            Value evaluateFunctionResult = functionFactory.
                    create(functionHolder.getFunctionName()).evaluate(functionHolder.getArguments());

            output.systemStack().current().pushOperand(evaluateFunctionResult);

            return true;
        }

        return false;
    }
}
