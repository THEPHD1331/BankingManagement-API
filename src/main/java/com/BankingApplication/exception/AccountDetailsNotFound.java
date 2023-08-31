package com.BankingApplication.exception;

public class AccountDetailsNotFound extends Exception{
    public AccountDetailsNotFound() {
    }

    public AccountDetailsNotFound(String message) {
        super(message);
    }

    public AccountDetailsNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
