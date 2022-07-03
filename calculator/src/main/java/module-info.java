module src {
    requires fsm;
    requires implementations;
    requires com.google.common;
    requires org.slf4j;

    exports com.teamdev.calculator;
    exports com.teamdev.calculator.math;
    exports com.teamdev.calculator.fsm.operand;
    exports com.teamdev.calculator.fsm.calculator;
    exports com.teamdev.calculator.fsm.function;
    exports com.teamdev.calculator.resolvers;
}
