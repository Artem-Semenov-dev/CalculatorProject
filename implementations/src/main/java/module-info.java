module implementations{
    requires fsm;
    requires com.google.common;
    requires org.slf4j;

    exports com.teamdev.implementations.datastructures;
    exports com.teamdev.implementations.type;
    exports com.teamdev.implementations.machines.expression;
    exports com.teamdev.implementations.machines.function;
    exports com.teamdev.implementations.machines.brackets;
    exports com.teamdev.implementations.machines.number;
    exports com.teamdev.implementations.operators;
}
