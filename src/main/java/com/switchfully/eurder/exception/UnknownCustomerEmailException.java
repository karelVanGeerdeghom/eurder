package com.switchfully.eurder.exception;

public class UnknownCustomerEmailException extends RuntimeException {
    public UnknownCustomerEmailException() {
        super("Unknown customer email");
    }
}
