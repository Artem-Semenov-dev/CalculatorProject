package com.teamdev.bazascript.interpreter.datastructure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;

public class DataStructureContext implements WithContext {

    private final ScriptContext scriptContext;

    private final DataStructureDefineContext defineContext;

    public DataStructureContext(ScriptContext scriptContext, DataStructureDefineContext defineContext) {
        this.scriptContext = scriptContext;
        this.defineContext = defineContext;
    }

    public DataStructureDefineContext getDefineContext() {
        return defineContext;
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
