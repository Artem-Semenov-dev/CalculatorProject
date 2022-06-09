package src.calculator;

import com.google.common.base.Preconditions;

import java.util.regex.Pattern;

public class MathematicalExpression {

    private static final Pattern COMPILE = Pattern.compile("\\s+");
    private final String expression;

    public MathematicalExpression(String expression) {
        Preconditions.checkNotNull(expression);
        this.expression = COMPILE.matcher(expression).replaceAll("");
    }

    public String getExpression() {
        return expression;
    }
}
