package src.calculator.resolvers;

import src.calculator.fsm.function.FunctionFactory;
import src.calculator.fsm.function.FunctionMachine;
import src.calculator.fsm.util.CharSequenceReader;
import src.calculator.fsm.util.FunctionHolder;
import src.calculator.fsm.util.ResolvingException;
import src.calculator.math.MathElementResolver;
import src.calculator.math.MathElementResolverFactory;

import java.util.Optional;

/**
 * {@code FunctionResolver} is an implementation of {@link MathElementResolver} that
 * resolve input chain for {@link FunctionMachine}.
 */

public class FunctionResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory functionFactory = new FunctionFactory();

    public FunctionResolver(MathElementResolverFactory factory) {

        this.elementResolverFactory = factory;
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        FunctionMachine functionMachine = FunctionMachine.create(elementResolverFactory);

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functionFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
