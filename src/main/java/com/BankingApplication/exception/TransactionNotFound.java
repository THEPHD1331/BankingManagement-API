package com.BankingApplication.exception;

public class TransactionNotFound extends Exception{

    public TransactionNotFound() {
    }

    public TransactionNotFound(String message) {
        super(message);
    }

    public TransactionNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
