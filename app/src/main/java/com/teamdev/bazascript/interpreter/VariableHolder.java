package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.TreeMap;

public class VariableHolder {

    private String variableData;

    private final Map<String, Double> variables = new TreeMap<>();


    public void putVariable(String name, Double value){

        Preconditions.checkNotNull(name, value);

        variables.put(name, value);
    }

    public Double getVarible(String name){

        return variables.get(name);
    }

    public String getVariableData(){

        return variableData;
    }

    public void setVariableData(String variableData) {
        Preconditions.checkNotNull(variableData);

        this.variableData = variableData;
    }
}
