package com.BankingApplication.service;

import com.BankingApplication.exception.LoanDetailsNotFound;
import com.BankingApplication.model.LoanRequest;
import com.BankingApplication.model.LoanTO;
import java.util.List;

public interface LoanService {
    List<LoanTO> getAllLoans() throws LoanDetailsNotFound;
    LoanTO getLoanById(int loanId) throws LoanDetailsNotFound;
    LoanTO getLoanByType(String loanType) throws LoanDetailsNotFound;
    LoanTO saveLoan(LoanRequest loanRequest) throws LoanDetailsNotFound;
    LoanTO updateLoan(LoanRequest loanRequest) throws LoanDetailsNotFound;
    String deleteLoanById(int loanId) throws LoanDetailsNotFound;
    String deleteLoanByType(String loanType) throws LoanDetailsNotFound;
}
