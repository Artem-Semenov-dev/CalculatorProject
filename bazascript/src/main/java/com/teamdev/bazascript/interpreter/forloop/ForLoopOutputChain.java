package com.teamdev.bazascript.interpreter.forloop;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;

/**
 * {@code ForLoopOutputChain} is an implementation of {@link WithContext}
 * which used like output chain for {@link ForLoopMachine}.
 * It stores condition statement position and statement for updating variable position of for loop.
 * {@link ForLoopMachine} need condition statement position to be stored for possibility to cycle the loop body.
 * Position of statement that update variable is needed for execute that statement after the body of loop was executed.
 */

class ForLoopOutputChain implements WithContext {

    private final ScriptContext scriptContext;

    private boolean initialParsingStatus;

    private boolean conditionValue;

    private int conditionPosition;

    private int updateVariablePosition;

    ForLoopOutputChain(ScriptContext scriptContext) {
        this.scriptContext = Preconditions.checkNotNull(scriptContext);
    }

    boolean getConditionValue() {
        return conditionValue;
    }

    void setConditionValue(boolean conditionValue) {
        this.conditionValue = conditionValue;
    }

    void parseOnly() {

        if (!initialParsingStatus) {
            scriptContext.setParsingPermission(true);
        }
    }

    void prohibitParseOnly() {

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

    @Override
    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    @Override
    public boolean isParseOnly() {
        return scriptContext.isParseOnly();
    }
}
