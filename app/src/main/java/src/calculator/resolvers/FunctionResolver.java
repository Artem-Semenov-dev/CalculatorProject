package src.calculator.resolvers;

import com.google.common.base.Preconditions;
import src.calculator.fsm.function.Function;
import src.calculator.fsm.function.FunctionHolder;
import src.calculator.fsm.function.FunctionMachine;
import src.calculator.fsm.util.CharSequenceReader;
import src.calculator.fsm.util.ResolvingException;
import src.calculator.math.MathElementResolver;
import src.calculator.math.MathElementResolverFactory;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class FunctionResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    private final Map<String, Function> functions = new TreeMap<>();

    public FunctionResolver(MathElementResolverFactory factory) {
        this.factory = factory;

        functions.put("min", arguments -> {
            Preconditions.checkState(arguments.size()>1, "Not enough arguments in min function");

            return arguments.stream().min(Double::compare).get();
        });

        functions.put("max", arguments -> {
            Preconditions.checkState(arguments.size()>1, "Not enough arguments in max function");

            return arguments.stream().max(Double::compare).get();
        });

        functions.put("avg", arguments -> {
            Preconditions.checkState(arguments.size()>1, "Not enough arguments in avg function");

            return arguments.stream().collect(Collectors.averagingDouble((a) -> a));
        });
        functions.put("sqrt", arguments -> {
            Preconditions.checkState(arguments.size() == 1);

            return StrictMath.sqrt(arguments.get(0));
        });
        functions.put("pi", arguments -> {
            Preconditions.checkState(arguments.isEmpty());
            return Math.PI;
        });

    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        FunctionMachine functionMachine = FunctionMachine.create(factory);

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functions.get(holder.getFunctionName()).evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
