package com.example.eurder.exception;

public class UnknownAdminEmailException extends RuntimeException {
    public UnknownAdminEmailException() {
        super("Unknown admin email");
    }
}
