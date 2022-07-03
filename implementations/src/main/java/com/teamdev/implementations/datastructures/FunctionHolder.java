package com.teamdev.implementations.datastructures;

import com.google.common.base.Preconditions;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code FunctionHolder} is a simple holder class that can be used like an output chain for
 * {@link IdentifierMachine} and {@link com.teamdev.implementations.machines.expression.ExpressionMachine}.
 * Class can be used for store data to make an instance of {@link com.teamdev.implementations.machines.function.Function}.
 */

public class FunctionHolder {

    private String functionName;
    private final List<Value> arguments;

    public FunctionHolder() {
        arguments = new ArrayList<>();
    }

    public void setFunctionName(String name){

        this.functionName = Preconditions.checkNotNull(name);
    }

    public void setArgument(Value argument){

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Value> getArguments() {
        return Collections.unmodifiableList(arguments);
    }
}
