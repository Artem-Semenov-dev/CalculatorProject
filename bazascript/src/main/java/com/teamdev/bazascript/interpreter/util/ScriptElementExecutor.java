package com.teamdev.bazascript.interpreter.util;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.fsm.CharSequenceReader;

/**
 * {@code ScriptElementExecutor} is a functional interface that can be used to
 * implement any code that can execute program on BazaScript language and
 * put result of execution to {@link ScriptContext}.
 */

@FunctionalInterface
public interface ScriptElementExecutor {

    boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException;
}
