package src.calculator.math;

import java.util.EnumMap;
import java.util.Map;

public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolver> resolvers = new EnumMap<>(MathElement.class);

    @Override
    public MathElementResolver create(MathElement mathElement) {
        return null;
    }
}
