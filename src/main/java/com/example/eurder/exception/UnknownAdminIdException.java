package com.example.eurder.exception;

public class UnknownAdminIdException extends RuntimeException {
    public UnknownAdminIdException() {
        super("Unknown admin id");
    }
}
