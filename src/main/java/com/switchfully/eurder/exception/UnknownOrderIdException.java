package com.switchfully.eurder.exception;

public class UnknownOrderIdException extends RuntimeException {
    public UnknownOrderIdException() {
        super("Unknown order id");
    }
}
