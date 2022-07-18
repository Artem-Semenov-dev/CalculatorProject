package com.teamdev.bazascript.interpreter.raeddatastructure;

/**
 * {@code DataStructureReadStates} enumeration that used in {@link DataStructureReadMachine}.
 */

enum DataStructureReadStates {

    START,
    DATA_STRUCTURE_NAME,
    DATA_STRUCTURE_FIELD,
    DOT,
    FINISH
}
