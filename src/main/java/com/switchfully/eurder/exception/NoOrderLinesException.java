package com.switchfully.eurder.exception;

public class NoOrderLinesException extends RuntimeException {
    public NoOrderLinesException() {
        super("No order lines exception");
    }
}
