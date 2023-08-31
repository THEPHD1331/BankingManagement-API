package com.BankingApplication.service;

import com.BankingApplication.exception.TransactionNotFound;
import com.BankingApplication.model.TransacTO;
import com.BankingApplication.model.TransactionRequest;

import java.util.List;

public interface TransactionService {

    List<TransacTO> getAllTransactions() throws TransactionNotFound;
    TransacTO getTransactionById(int transacId) throws TransactionNotFound;
    TransacTO saveTransaction(TransactionRequest transactionRequest) throws TransactionNotFound;
    String deleteTransaction(int transacId) throws TransactionNotFound;
}
