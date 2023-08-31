package com.BankingApplication.exception;

public class BankDetailsNotFound extends Exception{
    public BankDetailsNotFound() {
        super();
    }

    public BankDetailsNotFound(String message) {
        super(message);
    }
}
