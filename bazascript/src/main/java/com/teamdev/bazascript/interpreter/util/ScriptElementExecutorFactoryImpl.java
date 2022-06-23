package com.teamdev.bazascript.interpreter.util;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.calculator.fsm.expression.ExpressionMachine;
import com.teamdev.calculator.fsm.expression.ExpressionStates;
import com.teamdev.calculator.fsm.number.NumberStateMachine;
import com.teamdev.calculator.fsm.util.PrioritizedBinaryOperator;
import com.teamdev.calculator.fsm.util.ShuntingYard;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.ResolvingException;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

public class ScriptElementExecutorFactoryImpl implements ScriptElementExecutorFactory {

    private final Map<ScriptElement, ScriptElementExecutorCreator> executors = new EnumMap<>(ScriptElement.class);

    public ScriptElementExecutorFactoryImpl() {

        executors.put(ScriptElement.NUMBER, () -> (inputChain, output) -> {

            Optional<Double> execute = NumberStateMachine.execute(inputChain);

            if (execute.isPresent()) {
                output.systemStack().current().pushOperand(execute.get());

                return true;
            }

            return false;
        });

        executors.put(ScriptElement.EXPRESSION, () -> ((inputChain, output) -> new DetachedShuntingYardExecutor<>(ExpressionMachine.create(
                (scriptContext, prioritizedBinaryOperator) -> scriptContext.systemStack().current().pushOperator(prioritizedBinaryOperator),
                new ExecuteProgramElementTransducer(ScriptElement.OPERAND,
                        (scriptContext, aDouble) ->
                                scriptContext.systemStack().current().pushOperand(aDouble), this))).execute(inputChain, output)));

//        executors.put(ScriptElement.EXPRESSION, new ScriptElementExecutorCreator() {
//            @Override
//            public ScriptElementExecutor create() {
//                return new DetachedShuntingYardExecutor<>(
//                        ExpressionMachine.create(ShuntingYard::pushOperator,
//                                new ExecuteProgramElementTransducer(ScriptElement.OPERAND,
//                                        new BiConsumer<ScriptContext, Double>() {
//                                            @Override
//                                            public void accept(ScriptContext scriptContext, Double aDouble) {
//                                                scriptContext.systemStack().current().pushOperand(aDouble);
//                                            }
//                                        }, this))
//                );
//            }
//        });
    }

    @Override
    public ScriptElementExecutor create(ScriptElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

    public static class DetachedShuntingYardExecutor<I> implements ScriptElementExecutor {

        private final FiniteStateMachine<I, ScriptContext> machine;

        public DetachedShuntingYardExecutor(FiniteStateMachine<I, ScriptContext> machine) {
            this.machine = Preconditions.checkNotNull(machine);
        }

        @Override
        public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

            output.systemStack().create();

            if (machine.run(inputChain, output)){

                double peekResult = output.systemStack().close().peekResult();

                output.systemStack().current().pushOperand(peekResult);

                return true;
            }

            return false;
        }
    }
}
