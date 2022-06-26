package com.teamdev.calculator;

import com.google.common.testing.NullPointerTester;
import org.junit.jupiter.api.Test;

public class IntegrationTest {

    @Test
    public void TestNulls() {
        new NullPointerTester().testAllPublicInstanceMethods(new Calculator());
    }


}
