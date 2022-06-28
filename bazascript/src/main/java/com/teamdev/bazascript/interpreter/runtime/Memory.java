package com.teamdev.bazascript.interpreter.runtime;

import java.util.HashMap;
import java.util.Map;

/**
 *{@code Memory} is a class that used to store Double type variables.
 */

public class Memory {

    private final Map<String, Double> variables = new HashMap<>();

    public void setVariable(String identifier, Double value) {

        variables.put(identifier, value);
    }

    public Double getVariable(String identifier) {

        return variables.get(identifier);
    }

    public boolean hasVariable(String variableName) {

        return variables.containsKey(variableName);
    }

    public void clearMemory(){
        variables.clear();
    }
}
