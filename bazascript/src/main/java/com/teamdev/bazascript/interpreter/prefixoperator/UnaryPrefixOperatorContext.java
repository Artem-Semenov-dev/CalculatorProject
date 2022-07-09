package com.teamdev.bazascript.interpreter.prefixoperator;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.WithContext;
import com.teamdev.implementations.type.Value;

import java.util.function.UnaryOperator;

public class UnaryPrefixOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private UnaryOperator<Value> unaryOperator;

    UnaryPrefixOperatorContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    void setUnaryOperator(UnaryOperator<Value> unaryOperator) {
        this.unaryOperator = unaryOperator;
    }

    Value applyOperator(Value operand) {

        return unaryOperator.apply(operand);
    }

    @Override
    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    @Override
    public boolean isParseOnly() {
        return scriptContext.isParseOnly();
    }
}
