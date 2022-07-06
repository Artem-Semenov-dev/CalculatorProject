package com.teamdev.bazascript.interpreter.initvar.ternary;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;

public class TernaryOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean ternaryOperatorCondition;


    public TernaryOperatorContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    public boolean ternaryOperatorCondition() {
        return ternaryOperatorCondition;
    }

    public void setTernaryOperatorCondition(boolean ternaryOperatorCondition) {
        this.ternaryOperatorCondition = ternaryOperatorCondition;
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
