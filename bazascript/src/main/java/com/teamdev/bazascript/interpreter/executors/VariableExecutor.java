package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.variable.ProduceVariableTransducer;
import com.teamdev.bazascript.interpreter.util.UnaryPrefixOperatorContext;
import com.teamdev.bazascript.interpreter.variable.UnaryPrefixOperatorTransducer;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;

import java.util.List;

/**
 * {@code VariableExecutor} is an implementation of {@link ScriptElementExecutor}.
 * {@code VariableExecutor} is used run produceVariableMachine for
 * read variables or update them if they have unary prefix operators and pushed result to output.
 */

public class VariableExecutor implements ScriptElementExecutor {
    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        UnaryPrefixOperatorTransducer unaryPrefixOperatorTransducer = new UnaryPrefixOperatorTransducer();

        Transducer<UnaryPrefixOperatorContext, ExecutionException> produceVariableTransducer = new ProduceVariableTransducer();

        FiniteStateMachine<Object, UnaryPrefixOperatorContext, ExecutionException> produceVariableMachine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                }
                , List.of(unaryPrefixOperatorTransducer),
                List.of(unaryPrefixOperatorTransducer, produceVariableTransducer));

        UnaryPrefixOperatorContext unaryOperatorContext = new UnaryPrefixOperatorContext(output);

        return produceVariableMachine.run(inputChain, unaryOperatorContext);
    }
}
