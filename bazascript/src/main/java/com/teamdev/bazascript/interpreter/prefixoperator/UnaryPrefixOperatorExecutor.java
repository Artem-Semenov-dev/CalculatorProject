package com.teamdev.bazascript.interpreter.prefixoperator;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;

import java.util.List;

/**
 * {@code UnaryPrefixOperatorExecutor} is an implementation of {@link ScriptElementExecutor}.
 * {@code UnaryPrefixOperatorExecutor} is used to create and run {@link FiniteStateMachine}
 * for executing expression with unary prefix operator.
 */

public class UnaryPrefixOperatorExecutor implements ScriptElementExecutor {
    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Transducer<UnaryPrefixOperatorContext, ExecutionException> unaryOperatorTransducer = new UnaryOperatorTransducer();

        Transducer<UnaryPrefixOperatorContext, ExecutionException> updateVariableTransducer = new UpdateVariableTransducer();

        FiniteStateMachine<Object, UnaryPrefixOperatorContext, ExecutionException> machine =
                FiniteStateMachine.chainMachine(errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        List.of(),
                        List.of(unaryOperatorTransducer, updateVariableTransducer));

        UnaryPrefixOperatorContext unaryOperatorContext = new UnaryPrefixOperatorContext(output);

        return machine.run(inputChain, unaryOperatorContext);
    }
}
