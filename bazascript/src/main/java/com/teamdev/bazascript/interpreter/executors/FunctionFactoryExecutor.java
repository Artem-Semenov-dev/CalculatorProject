package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.FunctionHolderWithContext;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.calculator.fsm.function.FunctionFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.ResolvingException;

public class FunctionFactoryExecutor<I> implements ScriptElementExecutor {
    private final FiniteStateMachine<I, FunctionHolderWithContext> machine;

    public FunctionFactoryExecutor(FiniteStateMachine<I, FunctionHolderWithContext> machine) {
        this.machine = machine;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

        FunctionFactory functionFactory = new FunctionFactory();

        var functionHolder = new FunctionHolderWithContext(output);

        if (machine.run(inputChain, functionHolder)) {

            Double evaluateFunctionResult = functionFactory.
                    create(functionHolder.getFunctionName()).evaluate(functionHolder.getArguments());

            output.systemStack().current().pushOperand(evaluateFunctionResult);

            return true;
        }

        return false;
    }
}
