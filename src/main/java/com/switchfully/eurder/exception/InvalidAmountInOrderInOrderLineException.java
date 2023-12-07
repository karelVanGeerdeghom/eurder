package com.switchfully.eurder.exception;

public class InvalidAmountInOrderInOrderLineException extends RuntimeException {
    public InvalidAmountInOrderInOrderLineException() {
        super("Invalid amount in order in orderline");
    }
}