package com.teamdev.bazascript.interpreter.executors;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.ResolvingException;

public class DetachedShuntingYardExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, ScriptContext> machine;

    public DetachedShuntingYardExecutor(FiniteStateMachine<I, ScriptContext> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

        output.systemStack().create();

        if (machine.run(inputChain, output)) {

            double peekResult = output.systemStack().close().peekResult();

            output.systemStack().current().pushOperand(peekResult);

            return true;
        }

        return false;
    }
}
