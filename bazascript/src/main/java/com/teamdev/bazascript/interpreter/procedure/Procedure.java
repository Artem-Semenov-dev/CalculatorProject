package com.teamdev.bazascript.interpreter.procedure;

import com.teamdev.bazascript.interpreter.ProgramMemory;

import java.util.List;

public interface Procedure {

    void create(List<Double> arguments, ProgramMemory output);
}
