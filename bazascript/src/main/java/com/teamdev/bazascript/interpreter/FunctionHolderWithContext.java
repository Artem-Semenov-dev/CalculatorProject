package com.teamdev.bazascript.interpreter;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.WithContext;
import com.teamdev.fsm.identifier.IdentifierMachine;
import com.teamdev.implementations.type.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code FunctionHolderWithContext} is a simple holder class that implements interface {@link WithContext}
 * can be used like an output chain for {@link IdentifierMachine}.
 * Class can be used for store data to make an instance of {@link com.teamdev.implementations.machines.function.Function}.
 */


public class FunctionHolderWithContext implements WithContext {

    private final ScriptContext scriptContext;
    private final List<Value> arguments;
    private String functionName;

    public FunctionHolderWithContext(ScriptContext scriptContext) {
        this.scriptContext = Preconditions.checkNotNull(scriptContext);
        arguments = new ArrayList<>();
    }

    @Override
    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    @Override
    public boolean isParseonly() {
        return scriptContext.isParseonly();
    }

    void setArgument(Value argument) {

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    void setFunctionName(String name) {

        this.functionName = Preconditions.checkNotNull(name);
    }

    public List<Value> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

}
