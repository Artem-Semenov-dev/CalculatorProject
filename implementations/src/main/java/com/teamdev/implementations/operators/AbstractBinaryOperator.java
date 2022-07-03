package com.teamdev.implementations.operators;

import com.google.common.base.Preconditions;
import com.teamdev.implementations.type.Value;

import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;

/**
 * {@code PrioritizedBinaryOperator} is an implementation of {@link DoubleBinaryOperator}
 * which is {@link Comparable} and have a priority.
 * <p>
 * Note: this class has a natural ordering that is inconsistent with equals.
 * </p>
 */

public abstract class AbstractBinaryOperator implements BiFunction<Value, Value, Value>, Comparable<AbstractBinaryOperator> {
//public class PrioritizedBinaryOperator implements BiFunction<Value, Value, Value>, Comparable<PrioritizedBinaryOperator> {



    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    private final Priority priority;

//    private final BiFunction<Double, Double, Double> origin;

//    @Override
//    public Value apply(Value left, Value right) {
//        return null;
//    }

    @Override
    public int compareTo(AbstractBinaryOperator o) {

        return priority.compareTo(o.priority);
    }

    public AbstractBinaryOperator(Priority priority) {

        this.priority = Preconditions.checkNotNull(priority);
    }

//    @Override
//    public Double apply(Double left, Double right) {
//        return origin.apply(left, right);
//    }


//    @Override
//    public double applyAsDouble(double left, double right) {
//
//
//        return origin.applyAsDouble(left, right);
//    }

}
