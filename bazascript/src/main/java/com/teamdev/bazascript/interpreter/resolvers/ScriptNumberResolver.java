package com.teamdev.bazascript.interpreter.resolvers;

import com.teamdev.calculator.fsm.number.NumberStateMachine;
import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.fsm.ResolvingException;

import java.util.Optional;

public class ScriptNumberResolver implements MathElementResolver {
    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        StringBuilder stringBuilder = new StringBuilder(32);

        NumberStateMachine numberMachine = NumberStateMachine.create();

        if (numberMachine.run(inputChain, stringBuilder)){

            return Optional.of(Double.parseDouble(stringBuilder.toString()));
        }

        return Optional.empty();
    }
}
