package com.teamdev.bazascript.interpreter.procedure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcedureFactory {

    private final Map<String, Procedure> procedures = new HashMap<>();

    public ProcedureFactory() {

        procedures.put("print", (arguments, output) -> output.getOutput().print(arguments.toString()));

        procedures.put("clear", (arguments, output) -> output.memory().clearMemory());

    }

    public Procedure create(String procedureName) {

        return procedures.get(procedureName);
    }
}
