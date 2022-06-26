package com.teamdev.bazascript.interpreter.initvar;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.WithContext;

public class InitVarContext implements WithContext {

    private final ScriptContext scriptContext;

    private String variableName;

    private Double variableValue;

    public InitVarContext(ScriptContext scriptContext) {

        this.scriptContext = scriptContext;
    }

    String getVariableName() {
        return variableName;
    }

    void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    Double getVariableValue() {
        return variableValue;
    }

    void setVariableValue(Double variableValue) {
        this.variableValue = variableValue;
    }

    @Override
    public ScriptContext getContext() {
        return scriptContext;
    }
}
