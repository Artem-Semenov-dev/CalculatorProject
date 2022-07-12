package com.teamdev.bazascript.interpreter.whileoperator;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;

public class WhileOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean condition;

    private int position;

    WhileOperatorContext(ScriptContext context) {
        this.scriptContext = context;
    }

    int getPosition() {
        return position;
    }

    void setPosition(int position) {
        this.position = position;
    }

    boolean getConditionValue() {
        return condition;
    }

    void setConditionValue(boolean condition) {
        this.condition = condition;
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
