package com.example.eurder.exception;

public class UnknownCustomerEmailException extends RuntimeException {
    public UnknownCustomerEmailException() {
        super("Unknown customer email");
    }
}
