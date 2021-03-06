package com.teamdev.bazascript.interpreter.initvar.ternary;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.type.BooleanValueVisitor;
import com.teamdev.implementations.type.Value;

/**
 * {@code RelationalExpressionTransducer} is an implementation of {@link Transducer}
 * that used for producing result of relational expression to {@link TernaryOperatorContext}.
 */

class RelationalExpressionTransducer implements Transducer<TernaryOperatorContext, ExecutionException> {

    private final ScriptElementExecutor relationalExpressionExecutor;

    RelationalExpressionTransducer(ScriptElementExecutor relationalExpressionExecutor) {
        this.relationalExpressionExecutor = relationalExpressionExecutor;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, TernaryOperatorContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(outputChain, "InitVarContext is null inside RelationalExpressionTransducer");

        if (relationalExpressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            Value ternaryOperatorCondition = outputChain.getScriptContext().systemStack().current().result();

            outputChain.setTernaryOperatorCondition(BooleanValueVisitor.read(ternaryOperatorCondition));

            return true;
        }

        return false;
    }
}
