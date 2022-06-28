package com.teamdev.calculator.fsm.util;

import com.google.common.base.Preconditions;
import com.teamdev.calculator.fsm.expression.ExpressionMachine;
import com.teamdev.fsm.identifier.IdentifierMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code FunctionHolder} is a simple holder class that can be used like an output chain for
 * {@link IdentifierMachine} and {@link ExpressionMachine}.
 * Class can be used for store data to make an instance of {@link com.teamdev.calculator.fsm.function.Function}.
 */

public class FunctionHolder {

    private String functionName;
    private final List<Double> arguments;

    public FunctionHolder() {
        arguments = new ArrayList<>();
    }

    public void setFunctionName(String name){

        this.functionName = Preconditions.checkNotNull(name);
    }

    public void setArgument(Double argument){

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Double> getArguments() {
        return Collections.unmodifiableList(arguments);
    }
}
