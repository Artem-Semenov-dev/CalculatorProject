package com.teamdev.bazascript.interpreter.executors;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.implementations.type.Value;

public class DetachedShuntingYardExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, ScriptContext, ExecutionException> machine;

    public DetachedShuntingYardExecutor(FiniteStateMachine<I, ScriptContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {


        if (!output.isParseOnly()) {
            output.systemStack().create();
        }

        if (machine.run(inputChain, output)) {

            if (output.isParseOnly()) {
                return true;
            }

            Value peekResult = output.systemStack().close().result();

            output.systemStack().current().pushOperand(peekResult);

            return true;
        }

        if (!output.isParseOnly()) {
            output.systemStack().close();
        }

        return false;
    }
}