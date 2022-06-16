package com.teamdev.bazascript.calculator.resolvers;

import com.teamdev.bazascript.calculator.fsm.number.NumberStateMachine;
import com.teamdev.bazascript.calculator.fsm.util.CharSequenceReader;
import com.teamdev.bazascript.calculator.fsm.util.ResolvingException;
import com.teamdev.bazascript.calculator.math.MathElementResolver;

import java.util.Optional;

/**
 * {@code NumberResolver} is an implementation of {@link MathElementResolver} that
 * resolve input chain for {@link NumberStateMachine}.
 */

public class NumberResolver implements MathElementResolver {
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
