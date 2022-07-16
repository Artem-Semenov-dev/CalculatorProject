package com.teamdev.implementations.operators;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class StringSetConverter {

    private StringSetConverter() {
    }

    public static Set<Character> toCharacterSet(Map<String, AbstractBinaryOperator> convertibleObject){

        Set<Character> list = new HashSet<>();

        convertibleObject.keySet().forEach(s -> {
            for (Character ch: s.toCharArray()){
                list.add(ch);
            }
        });

        return list;
    }
}
