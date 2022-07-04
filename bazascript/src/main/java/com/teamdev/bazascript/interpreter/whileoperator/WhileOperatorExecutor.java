package com.teamdev.bazascript.interpreter.whileoperator;

import com.google.common.base.Preconditions;
import com.teamdev.bazascript.interpreter.runtime.ScriptContext;
import com.teamdev.bazascript.interpreter.util.*;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.FiniteStateMachine;
import com.teamdev.fsm.Transducer;
import com.teamdev.implementations.type.BooleanValueVisitor;

import java.util.List;

public class WhileOperatorExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public WhileOperatorExecutor(ScriptElementExecutorFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Transducer<WhileOperatorContext, ExecutionException> keyword = new Transducer<>() {
            @Override
            public boolean doTransition(CharSequenceReader inputChain, WhileOperatorContext outputChain) throws ExecutionException {

                outputChain.setPosition(inputChain.position());

                List<Transducer<WhileOperatorContext, ExecutionException>> keyword = Transducer.keyword("while");

                for (Transducer<WhileOperatorContext, ExecutionException> transducer : keyword) {

                    if (!transducer.doTransition(inputChain, outputChain)) {
                        return false;
                    }
                }

                return true;
            }
        };

        Transducer<WhileOperatorContext, ExecutionException> relationTransducer =
                new FunctionTransducer<>((whileOperatorContext, value) -> {
                    whileOperatorContext.setCondition(BooleanValueVisitor.read(value));
                },
                        factory, ScriptElement.RELATIONAL_EXPRESSION);

        Transducer<WhileOperatorContext, ExecutionException> programTransducer =
                (inputChain1, outputChain) -> {

                    ScriptElementExecutor executor = factory.create(ScriptElement.PROGRAM);

//                    ProgramMachine programMachine = ProgramMachine.create(factory, errorMessage -> {
//                        throw new ExecutionException(errorMessage);
//                    });

                    return executor.execute(inputChain1, outputChain.getScriptContext());
                };

        List<Transducer<WhileOperatorContext, ExecutionException>> transducers = List.of(keyword,
                Transducer.checkAndPassChar('('),
                relationTransducer,
                Transducer.<WhileOperatorContext, ExecutionException>checkAndPassChar(')')
                        .and(new Transducer<WhileOperatorContext, ExecutionException>() {
                            @Override
                            public boolean doTransition(CharSequenceReader inputChain, WhileOperatorContext outputChain) throws ExecutionException {

                                if (!outputChain.isCondition()) {

                                    outputChain.getScriptContext().setParsingPermission(true);
                                }

                                return true;
                            }
                        }),
                Transducer.checkAndPassChar('{'),
                programTransducer,
                Transducer.<WhileOperatorContext, ExecutionException>checkAndPassChar('}')
                        .and(new Transducer<WhileOperatorContext, ExecutionException>() {
                            @Override
                            public boolean doTransition(CharSequenceReader inputChain, WhileOperatorContext outputChain) throws ExecutionException {

                                if (outputChain.isCondition()) {

                                    inputChain.setPosition(outputChain.getPosition());

                                    ScriptElementExecutor executor = factory.create(ScriptElement.WHILE_OPERATOR);

                                    executor.execute(inputChain, outputChain.getScriptContext());
                                }

                                outputChain.getScriptContext().setParsingPermission(false);

                                return true;
                            }
                        }));


        var machine = FiniteStateMachine.chainMachine(errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, List.of(keyword), transducers);

        WhileOperatorContext whileOperatorContext = new WhileOperatorContext(output);

        return machine.run(inputChain, whileOperatorContext);
    }
}
