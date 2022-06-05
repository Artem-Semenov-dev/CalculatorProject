package src;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class NumberParcingTest {

    PreparedTest preparedTest = new PreparedTest();

    @ParameterizedTest
    @CsvFileSource(resources = "/PositiveCasesForNumberTest.csv")
    void testPositiveCase(String mathExpression, double resultExpected, String errorMessage) throws WrongExpressionException {

        preparedTest.positiveCase(mathExpression, resultExpected, errorMessage);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/NegativeCasesForNumberTest.csv")
    void testNegativeCase(String mathExpression, int expectedErrorPosition, String errorMessage) throws WrongExpressionException {

        preparedTest.negativeCase(mathExpression, expectedErrorPosition, errorMessage);
    }


}
