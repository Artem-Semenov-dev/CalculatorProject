package com.teamdev.bazascript.interpreter.executors;

import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.ExecutionException;
import com.teamdev.bazascript.interpreter.util.ScriptElementExecutor;
import com.teamdev.bazascript.interpreter.variable.ProduceVariableTransducer;
import com.teamdev.bazascript.interpreter.util.UnaryPrefixOperatorContext;
import com.teamdev.bazascript.interpreter.variable.UnaryPrefixOperatorTransducer;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;

import java.util.List;

public class VariableExecutor implements ScriptElementExecutor {


    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        UnaryPrefixOperatorTransducer unaryPrefixOperatorTransducer = new UnaryPrefixOperatorTransducer();

        ProduceVariableTransducer produceVariableTransducer = new ProduceVariableTransducer();

        FiniteStateMachine<Object, UnaryPrefixOperatorContext, ExecutionException> machine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                }
                , List.of(unaryPrefixOperatorTransducer),
                List.of(unaryPrefixOperatorTransducer, produceVariableTransducer));

        UnaryPrefixOperatorContext unaryOperatorContext = new UnaryPrefixOperatorContext(output);

        return machine.run(inputChain, unaryOperatorContext);
    }
}
