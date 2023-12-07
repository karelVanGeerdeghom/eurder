package com.switchfully.eurder.exception;

public class UnknownCustomerIdException extends RuntimeException {
    public UnknownCustomerIdException() {
        super("Unknown customer id");
    }
}
