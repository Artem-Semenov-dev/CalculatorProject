package com.teamdev.bazascript.interpreter.forloop;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;

/**
 * {@code ParseUpdateVariableStatementTransducer} is an implementation of {@link Transducer}
 * that create and execute {@link ScriptElementExecutor} for statement that must update variable in for loop.
 * This transducer can only parse statement and save it position, to see details go to {@link ForLoopOutputChain}.
 */

class ParseUpdateVariableStatementTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    ParseUpdateVariableStatementTransducer(ScriptElementExecutorFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        outputChain.parseOnly();

        ScriptElementExecutor updateVariableExecutor = factory.create(ScriptElement.STATEMENT);

        outputChain.setUpdateVariablePosition(inputChain.position());

        if (updateVariableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.getConditionValue()) {

                outputChain.prohibitParseOnly();
            }

            return true;
        }

        return false;
    }
}
