package com.teamdev.bazascript.interpreter.whileoperator;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;

public class WhileOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean condition;

    private int position;

    public WhileOperatorContext(ScriptContext context) {
        this.scriptContext = context;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
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
