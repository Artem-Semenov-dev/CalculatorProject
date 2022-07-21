package com.teamdev.bazascript.interpreter.forloop;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

/**
 * {@code UpdateVariableTransducer} is an implementation of {@link Transducer}
 * that create and execute {@link ScriptElementExecutor} for statement that must update variable in for loop.
 * Also, it jumps to condition of for loop, to see details go to {@link ForLoopOutputChain}.
 */

class UpdateVariableTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    UpdateVariableTransducer(ScriptElementExecutorFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        ScriptElementExecutor updateVariableExecutor = factory.create(ScriptElement.STATEMENT);

        if (updateVariableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {

                return true;
            }

            inputChain.setPosition(outputChain.getConditionPosition());

            return true;
        }

        return false;
    }
}
