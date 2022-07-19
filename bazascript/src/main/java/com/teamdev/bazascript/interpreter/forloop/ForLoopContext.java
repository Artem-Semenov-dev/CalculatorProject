package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.implementations.type.Value;

public class ForLoopContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean condition;

    private Value initializedVariable;

    public boolean condition() {
        return condition;
    }

    public Value getInitializedVariable() {
        return initializedVariable;
    }

    public void setConditionValue(boolean condition) {
        this.condition = condition;
    }

    public void setInitializedVariable(Value initializedVariable) {
        this.initializedVariable = initializedVariable;
    }

    public ForLoopContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
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
