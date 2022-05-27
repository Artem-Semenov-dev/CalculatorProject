package src;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    @Test
    public void testTwoPlusTwoProgram(){
        MathematicalExpression mathExpression = new MathematicalExpression("2+2");

        CalculationResult calculationResult = new CalculatorAPI().Calculate(mathExpression);

        double result = 4;

        assertEquals(result, calculationResult.getResult());
    }

    @Test
    public void testComplexProgram(){
        MathematicalExpression mathExpression = new MathematicalExpression("(255+5)/(34*89)+min(23,45)");

        CalculationResult calculationResult = new CalculatorAPI().Calculate(mathExpression);

        double result = (255+5)/(34*89)+23;

        assertEquals(result, calculationResult.getResult());
    }
}
