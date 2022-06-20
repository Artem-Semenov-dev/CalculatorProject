module src {
    requires fsm;
    requires com.google.common;
    requires org.slf4j;
    exports com.teamdev.calculator;
    exports com.teamdev.calculator.math;
    exports com.teamdev.calculator.fsm.identifier;
}
