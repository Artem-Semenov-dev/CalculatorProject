package com.teamdev.bazascript.interpreter.initvar;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.implementations.type.Value;

/**
 * {@code InitVarContext} is a class that used for variable initialisation, as an output for {@link InitVarMachine}.
 */

public class InitVarContext implements WithContext {

    private final ScriptContext scriptContext;

    private String variableName;

    private Value variableValue;

    public InitVarContext(ScriptContext scriptContext) {

        this.scriptContext = scriptContext;
    }

    String getVariableName() {
        return variableName;
    }

    void setVariableName(String variableName) {
        this.variableName = Preconditions.checkNotNull(variableName);
    }

    Value getVariableValue() {
        return variableValue;
    }

    void setVariableValue(Value variableValue) {
        this.variableValue = Preconditions.checkNotNull(variableValue);
    }

    @Override
    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    @Override
    public boolean isParseonly() {
        return scriptContext.isParseonly();
    }
}
