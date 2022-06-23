package com.teamdev.bazascript.interpreter.util;

@FunctionalInterface
public interface ScriptElementExecutorCreator {

    ScriptElementExecutor create();
}
