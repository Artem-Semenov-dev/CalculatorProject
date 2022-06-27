package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.FunctionHolderWithContext;
import com.teamdev.bazascript.interpreter.procedure.ProcedureFactory;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.calculator.ResolvingException;

public class ProcedureFactoryExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine;

    public ProcedureFactoryExecutor(FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine) {
        this.machine = machine;
    }


    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        FunctionHolderWithContext functionHolder = new FunctionHolderWithContext(output);

        ProcedureFactory procedureFactory = new ProcedureFactory();

        if (machine.run(inputChain, functionHolder)) {

            procedureFactory.create(functionHolder.getFunctionName())
                    .execute(functionHolder.getArguments(), functionHolder.getContext());

            return true;
        }

        return false;

    }
}
