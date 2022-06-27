package com.teamdev.bazascript.interpreter.util;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.fsm.CharSequenceReader;

@FunctionalInterface
public interface ScriptElementExecutor {

    boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException;
}
