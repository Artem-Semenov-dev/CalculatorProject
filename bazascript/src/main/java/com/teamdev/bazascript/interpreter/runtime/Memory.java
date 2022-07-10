package com.teamdev.bazascript.interpreter.runtime;

import com.google.common.base.Preconditions;
import com.teamdev.implementations.type.DoubleValueVisitor;
import com.teamdev.implementations.type.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * {@code Memory} is a class that used to store Double type variables.
 */

public class Memory {

    private static final Logger logger = LoggerFactory.getLogger(Memory.class);

    private final Map<String, Value> variables = new HashMap<>();

    private final Map<String, Value> cache = new HashMap<>();

    public void setVariable(String identifier, Value value) {

        Preconditions.checkNotNull(identifier, value);

        variables.put(identifier, value);

        cache.put(identifier, value);
    }

    public void updateVariables(){

        variables.putAll(cache);
    }

    public void updateCache(){

        cache.putAll(variables);
    }

    public void clearCache(){

        cache.clear();
    }

    public Value getVariableValue(String identifier) {

        return variables.get(Preconditions.checkNotNull(identifier));
    }

    public Value getVariableValueFromCache(String identifier) {

        return cache.get(Preconditions.checkNotNull(identifier));
    }

    boolean hasVariable(String variableName) {

        return variables.containsKey(Preconditions.checkNotNull(variableName));
    }

    public void setVariableToCache(String identifier, Value value) {

        Preconditions.checkNotNull(identifier, value);

        if (logger.isInfoEnabled()){

            logger.info("Set variable to cache -> {}", DoubleValueVisitor.read(value));
        }

        cache.put(identifier, value);
    }

    public void clearMemory() {
        variables.clear();
        cache.clear();
    }
}
