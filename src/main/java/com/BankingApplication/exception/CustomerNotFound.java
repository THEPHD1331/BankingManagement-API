package com.BankingApplication.exception;

public class CustomerNotFound extends Exception{
    public CustomerNotFound() {
    }

    public CustomerNotFound(String message) {
        super(message);
    }

    public CustomerNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
