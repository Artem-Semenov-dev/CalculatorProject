package com.teamdev.bazascript.interpreter.datastructure;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class DataStructureDefineContext {

    private String structureName;

    private final Set<String> fieldNames = new LinkedHashSet<>();

    public void setStructureName(String structureName) {

        this.structureName = structureName;
    }

    public void addFieldName(String fieldName) {

        fieldNames.add(fieldName);
    }

    public String getStructureName() {

        return structureName;
    }

    public Set<String> getFieldNames() {

        return Collections.unmodifiableSet(fieldNames);
    }
}
