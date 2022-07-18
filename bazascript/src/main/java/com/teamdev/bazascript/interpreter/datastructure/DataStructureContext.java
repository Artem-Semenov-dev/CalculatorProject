package com.teamdev.bazascript.interpreter.datastructure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.runtime.WithContext;
import com.teamdev.implementations.type.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code DataStructureContext} is an implementation of {@link WithContext}.
 * {@code DataStructureContext} can be used like output chain for fill data structure executor machine.
 */

public class DataStructureContext implements WithContext {

    private final ScriptContext scriptContext;

    private String structureName;

    private final Map<String, Value> fields = new HashMap<>();

    DataStructureContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    String getStructureName() {
        return structureName;
    }

    void addFieldValue(String fieldName, Value fieldValue){

        fields.put(fieldName, fieldValue);
    }

    Map<String, Value> getFields() {

        return Collections.unmodifiableMap(fields);
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
