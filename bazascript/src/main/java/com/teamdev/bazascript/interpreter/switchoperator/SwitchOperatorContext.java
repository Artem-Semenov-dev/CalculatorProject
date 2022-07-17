package com.teamdev.bazascript.interpreter.switchoperator;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.implementations.type.Value;

import java.util.Objects;

/**
 * {@code SwitchOperatorContext} is an implementation of {@link WithContext}
 * that used like an output chain for {@link SwitchOperatorMachine}.
 */

public class SwitchOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private Value comparedValue;

    private boolean caseExecuted;

    public SwitchOperatorContext(ScriptContext scriptContext) {

        this.scriptContext = scriptContext;
    }

    void setComparedValue(Value comparedValue) {

        this.comparedValue = comparedValue;
    }

    boolean checkCondition(Value caseValue) {

        return Objects.equals(comparedValue, caseValue);
    }

    boolean isCaseExecuted() {

        return caseExecuted;
    }

    void setCaseExecuted() {

        this.caseExecuted = true;
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
