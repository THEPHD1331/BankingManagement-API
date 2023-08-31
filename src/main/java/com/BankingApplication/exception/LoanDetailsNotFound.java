package com.BankingApplication.exception;

public class LoanDetailsNotFound extends Exception{
    public LoanDetailsNotFound() {
    }

    public LoanDetailsNotFound(String message) {
        super(message);
    }

    public LoanDetailsNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
