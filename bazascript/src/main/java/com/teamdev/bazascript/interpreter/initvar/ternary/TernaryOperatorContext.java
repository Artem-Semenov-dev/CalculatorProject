package com.teamdev.bazascript.interpreter.initvar.ternary;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;

/**
 * {@code TernaryOperatorContext} is an implementation of {@link WithContext}.
 * {@code TernaryOperatorContext} ia a simple holder class that can be used like an output chain for {@link TernaryOperatorMachine}.
 */

public class TernaryOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean ternaryOperatorCondition;


    public TernaryOperatorContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    boolean ternaryOperatorCondition() {
        return ternaryOperatorCondition;
    }

    void setTernaryOperatorCondition(boolean ternaryOperatorCondition) {
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
