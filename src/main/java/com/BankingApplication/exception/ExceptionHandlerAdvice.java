package com.BankingApplication.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BankDetailsNotFound.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleBankException(BankDetailsNotFound ex, HttpServletRequest request){
        log.error("Exception while processing bank details", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }

    @ExceptionHandler(BranchDetailsNotFound.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleBranchException(BankDetailsNotFound ex, HttpServletRequest request){
        log.error("Exception while processing branch details", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }

    @ExceptionHandler(AccountDetailsNotFound.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleAccountException(BankDetailsNotFound ex, HttpServletRequest request){
        log.error("Exception while processing account details", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }

    @ExceptionHandler(LoanDetailsNotFound.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleLoanException(BankDetailsNotFound ex, HttpServletRequest request){
        log.error("Exception while processing loan details", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }

    @ExceptionHandler(CustomerNotFound.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleCustomerException(BankDetailsNotFound ex, HttpServletRequest request){
        log.error("Exception while processing bank details", ex);
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(ex.getMessage());
        response.setRequestedURI(request.getRequestURI());
        return  response;
    }
}
