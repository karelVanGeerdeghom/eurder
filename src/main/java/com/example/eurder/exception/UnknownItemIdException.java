package com.example.eurder.exception;

public class UnknownItemIdException extends RuntimeException {
    public UnknownItemIdException() {
        super("Unknown item id");
    }
}
