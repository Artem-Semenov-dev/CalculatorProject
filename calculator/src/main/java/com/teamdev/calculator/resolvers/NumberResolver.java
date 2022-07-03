package com.teamdev.calculator.resolvers;


import com.teamdev.calculator.math.MathElementResolver;
import com.teamdev.fsm.CharSequenceReader;
import com.teamdev.calculator.ResolvingException;
import com.teamdev.implementations.machines.number.NumberStateMachine;
import com.teamdev.implementations.type.Value;

import java.util.Optional;

/**
 * {@code NumberResolver} is an implementation of {@link MathElementResolver} that
 * resolve input chain for {@link NumberStateMachine}.
 */

public class NumberResolver implements MathElementResolver {
    @Override
    public Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException{

        return NumberStateMachine.execute(inputChain, errorMessage -> {throw new ResolvingException(errorMessage);});
    }
}
