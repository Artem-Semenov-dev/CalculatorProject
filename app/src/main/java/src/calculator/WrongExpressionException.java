package src.calculator;

import com.google.common.base.Preconditions;

/**
 * {@code WrongExpressionException} throw when {@link MathematicalExpression} contains syntax errors.
 * Provides position of syntax error in expression.
 */

public class WrongExpressionException extends Exception {

    private final int errorPosition;

    public WrongExpressionException(String message, int errorPosition) {
        super(Preconditions.checkNotNull(message));

        Preconditions.checkArgument(errorPosition >= 0);
        this.errorPosition = errorPosition;
    }

    public int getErrorPosition() {
        return errorPosition;
    }
}
