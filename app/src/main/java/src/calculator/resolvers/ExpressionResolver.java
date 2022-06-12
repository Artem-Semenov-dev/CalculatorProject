package src.calculator.resolvers;

import com.google.common.base.Preconditions;
import src.calculator.fsm.expression.ExpressionMachine;
import src.calculator.fsm.util.CharSequenceReader;
import src.calculator.fsm.util.ResolvingException;
import src.calculator.fsm.util.ShuntingYardStack;
import src.calculator.math.MathElementResolver;
import src.calculator.math.MathElementResolverFactory;

import java.util.Optional;

public class ExpressionResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    public ExpressionResolver(MathElementResolverFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {
        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        ExpressionMachine expressionMachine = ExpressionMachine.create(factory);

        if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
