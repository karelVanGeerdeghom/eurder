package com.example.eurder.exception;

public class UnknownAdminUsernameException extends RuntimeException {
    public UnknownAdminUsernameException() {
        super("Unknown admin");
    }
}
