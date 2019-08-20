package com.github.dnvriend.exeptions;

public class EngineNotStartedException extends RuntimeException {
    public EngineNotStartedException(String message) {
        super(message);
    }
}
