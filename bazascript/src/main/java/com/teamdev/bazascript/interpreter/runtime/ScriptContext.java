package com.teamdev.bazascript.interpreter.runtime;

public class ScriptContext {

    private final SystemStack systemStack = new SystemStack();

    private final Memory memory = new Memory();

    private final Output output = new Output();

    public Memory memory() {

        return memory;
    }

    public SystemStack systemStack() {

        return systemStack;
    }

    public Output getOutput() {
        return output;
    }
}
