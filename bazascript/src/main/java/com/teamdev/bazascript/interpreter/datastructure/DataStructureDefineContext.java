package com.teamdev.bazascript.interpreter.datastructure;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

class DataStructureDefineContext {

    private String structureName;

    private final Set<String> fieldNames = new LinkedHashSet<>();

    void setStructureName(String structureName) {

        this.structureName = structureName;
    }

    void addFieldName(String fieldName) {

        fieldNames.add(fieldName);
    }

    String getStructureName() {

        return structureName;
    }

    Set<String> getFieldNames() {

        return Collections.unmodifiableSet(fieldNames);
    }
}
