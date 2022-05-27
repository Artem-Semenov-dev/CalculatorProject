package src;

import com.google.common.base.Preconditions;

public class CalculationResult {

    private final double result;

    public CalculationResult(double result) {
        Preconditions.checkNotNull(result);

        this.result = result;
    }

    public double getResult() {
        return result;
    }
}
