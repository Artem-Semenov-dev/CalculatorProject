package com.teamdev.bazascript.interpreter.runtime;

import com.google.common.base.Preconditions;
import com.teamdev.implementations.type.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * {@code Memory} is a class that used to store Double type variables.
 */

public class Memory {

    private static final Logger logger = LoggerFactory.getLogger(Memory.class);

    private final Map<String, Value> variables = new HashMap<>();

    private final Map<String, Set<String>> structureTemplates = new HashMap<>();

    public void addStructureTemplate(String structureName, Set<String> fields){

        structureTemplates.put(structureName, fields);

        if (logger.isInfoEnabled()){

            logger.info("Put data structure template -> {}, with name -> {}", getTemplate(structureName), structureName);
        }
    }

    public Set<String> getTemplate(String structureName){

        return structureTemplates.get(structureName);
    }

    public void setVariable(String identifier, Value value) {

        Preconditions.checkNotNull(identifier, value);

        variables.put(identifier, value);
    }

    public Value getVariable(String identifier) {

        return variables.get(Preconditions.checkNotNull(identifier));
    }

    public boolean hasVariable(String variableName) {

        return variables.containsKey(Preconditions.checkNotNull(variableName));
    }

    public void clearMemory() {
        variables.clear();
    }
}
