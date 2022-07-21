package com.teamdev.bazascript.interpreter.forloop;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;

class ForLoopOutputChain implements WithContext {

    private final ScriptContext scriptContext;

    private boolean initialParsingStatus;

    private boolean conditionValue;

    private int conditionPosition;

    private int updateVariablePosition;

    boolean getConditionValue() {
        return conditionValue;
    }

    void setConditionValue(boolean conditionValue) {
        this.conditionValue = conditionValue;
    }

    void parseOnly(){

        if (!initialParsingStatus) {
            scriptContext.setParsingPermission(true);
        }
    }

    void prohibitParseOnly(){

        if (!initialParsingStatus) {
            scriptContext.setParsingPermission(false);
        }
    }


    void setInitialParsingStatus(boolean initialParcingStatus) {
        this.initialParsingStatus = initialParcingStatus;
    }


    int getConditionPosition() {

        return conditionPosition;
    }

    void setConditionPosition(int conditionPosition) {

        this.conditionPosition = conditionPosition;
    }

    int getUpdateVariablePosition() {
        return updateVariablePosition;
    }

    void setUpdateVariablePosition(int updateVariablePosition) {
        this.updateVariablePosition = updateVariablePosition;
    }


    ForLoopOutputChain(ScriptContext scriptContext) {
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
