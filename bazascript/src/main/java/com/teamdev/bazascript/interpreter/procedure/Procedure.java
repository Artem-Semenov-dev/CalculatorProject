package com.teamdev.bazascript.interpreter.procedure;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;

import java.util.List;

public interface Procedure {

    void execute(List<Double> arguments, ScriptContext output);
}
