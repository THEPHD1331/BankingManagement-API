package com.BankingApplication.exception;

public class BranchDetailsNotFound extends Exception{

    public BranchDetailsNotFound() {
    }

    public BranchDetailsNotFound(String message) {
        super(message);
    }

    public BranchDetailsNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
