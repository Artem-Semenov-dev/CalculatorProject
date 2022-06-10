package src.calculator.math;

import com.google.common.base.Preconditions;
import src.calculator.fsm.DeadlockException;
import src.calculator.fsm.brackets.BracketsMachine;
import src.calculator.fsm.expression.ShuntingYardStack;

import java.util.Optional;

public class BracketsResolver implements MathElementResolver {

    MathElementResolverFactory factory;

    BracketsResolver(MathElementResolverFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException, DeadlockException {
        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        BracketsMachine bracketsMachine = BracketsMachine.create(factory);

        if (bracketsMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
