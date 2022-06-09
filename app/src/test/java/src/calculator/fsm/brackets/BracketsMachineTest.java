package src.calculator.fsm.brackets;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import src.calculator.PreparedTest;
import src.calculator.WrongExpressionException;

public class BracketsMachineTest {

    private final PreparedTest preparedTest = new PreparedTest();

    @ParameterizedTest
    @CsvFileSource(resources = "/PositiveCasesForBracketsTest.csv")
    void testPositiveCase(String mathExpression, double resultExpected, String errorMessage) throws WrongExpressionException {

        preparedTest.positiveCase(mathExpression, resultExpected, errorMessage);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/NegativeCasesForBracketsTest.csv")
    void testNegativeCase(String mathExpression, int expectedErrorPosition, String errorMessage) {

        preparedTest.negativeCase(mathExpression, expectedErrorPosition, errorMessage);
    }
}
