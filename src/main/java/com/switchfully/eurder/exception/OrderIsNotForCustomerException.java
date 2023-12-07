package com.switchfully.eurder.exception;

public class OrderIsNotForCustomerException extends RuntimeException {
    public OrderIsNotForCustomerException() {
        super("Order is not for customer");
    }
}
