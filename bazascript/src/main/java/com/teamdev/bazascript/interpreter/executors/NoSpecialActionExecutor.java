package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.ResolvingException;

public class NoSpecialActionExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, ScriptContext> machine;

    public NoSpecialActionExecutor(FiniteStateMachine<I, ScriptContext> machine) {
        this.machine = machine;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

        return machine.run(inputChain, output);
    }
}
