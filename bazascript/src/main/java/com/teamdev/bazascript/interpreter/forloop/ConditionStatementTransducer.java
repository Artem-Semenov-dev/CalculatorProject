package com.teamdev.bazascript.interpreter.forloop;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElement;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutorFactory;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.type.BooleanValueVisitor;
import com.teamdev.implementations.type.Value;

/**
 * {@code ConditionStatementTransducer} is an implementation of {@link Transducer}
 * that produce value of loop condition to {@link ForLoopOutputChain}.
 * Also {@code ConditionStatementTransducer} gives permission to only parsing input chain
 * in case of value of loop condition is false.
 */

class ConditionStatementTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    ConditionStatementTransducer(ScriptElementExecutorFactory factory) {

        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        ScriptElementExecutor relationalExecutor = factory.create(ScriptElement.RELATIONAL_EXPRESSION);

        outputChain.getScriptContext().systemStack().create();

        if (relationalExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            Value conditionValue = outputChain.getScriptContext().systemStack().current().result();

            outputChain.getScriptContext().systemStack().close();

            if (BooleanValueVisitor.isBoolean(conditionValue)) {

                outputChain.setConditionValue(BooleanValueVisitor.read(conditionValue));

                if (!outputChain.getConditionValue()) {

                    outputChain.parseOnly();
                }

                return true;
            }

            return false;
        }

        outputChain.getScriptContext().systemStack().close();

        return false;
    }
}