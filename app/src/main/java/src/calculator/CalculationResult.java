package src.calculator;

import com.google.common.base.Preconditions;

public class CalculationResult {

    private final double result;

    CalculationResult(double result) {
        Preconditions.checkNotNull(result);

        this.result = result;
    }

    public double getResult() {
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
