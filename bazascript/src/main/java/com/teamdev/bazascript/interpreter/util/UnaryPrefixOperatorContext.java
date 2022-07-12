package com.teamdev.bazascript.interpreter.util;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.implementations.type.Value;

import java.util.function.UnaryOperator;

/**
 * {@code UnaryPrefixOperatorContext} is an implementation of {@link WithContext}.
 * {@code UnaryPrefixOperatorContext} can be used like an output chain for machine that execute expressions with unary prefix operator.
 */

public class UnaryPrefixOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean readVariableOnly;

    private UnaryOperator<Value> unaryOperator;

    public UnaryPrefixOperatorContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    public void setUnaryOperator(UnaryOperator<Value> unaryOperator) {
        this.unaryOperator = unaryOperator;
    }

    public void setReadVariableOnlyValue(boolean readVariableOnly) {
        this.readVariableOnly = readVariableOnly;
    }

    public boolean readVariableOnly() {
        return readVariableOnly;
    }

    public Value applyOperator(Value operand) {

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
