package com.BankingApplication.service;

import com.BankingApplication.entity.Transaction;
import com.BankingApplication.exception.TransactionNotFound;
import com.BankingApplication.model.TransacTO;
import com.BankingApplication.model.TransactionRequest;
import com.BankingApplication.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepo;

    @Override
    public List<TransacTO> getAllTransactions() throws TransactionNotFound {

        log.info("Inside transactionServiceImpl");
        List<Transaction> transactions= transactionRepo.findAll();
        log.info("List of Transactions: {}", transactions);

        if(CollectionUtils.isEmpty(transactions)){
            log.info("Transaction Details not found");
            throw new TransactionNotFound("Transaction Details not found");
        }
        List<TransacTO> transacTOList= transactions.stream().map(transaction -> {

            TransacTO transacTO= new TransacTO();
            transacTO.setTransacId(transaction.getTransacId());
            transacTO.setTransacAmt(transaction.getTransacAmount());
            transacTO.setTransacDate(transaction.getTransacDate());
            transacTO.setCustId(transaction.getCustId());
            transacTO.setTransacStatus(transaction.getTransacStatus());
            return transacTO;
        }).collect(Collectors.toList());

        return transacTOList;
    }

    @Override
    public TransacTO getTransactionById(int transacId) throws TransactionNotFound {
        log.info("Inside getTransac byId ");
        Optional<Transaction> transaction= transactionRepo.findById(transacId);

        if(!transaction.isPresent()){
            log.info("Transaction Details not found");
            throw new TransactionNotFound("Transaction Details not found");
        }
        Transaction transaction1= transaction.get();
        TransacTO transacTO= new TransacTO();
        transacTO.setTransacId(transaction1.getTransacId());
        transacTO.setTransacAmt(transaction1.getTransacAmount());
        transacTO.setTransacDate(transaction1.getTransacDate());
        transacTO.setCustId(transaction1.getCustId());
        transacTO.setTransacStatus(transaction1.getTransacStatus());

        log.info("End of get Transac By Id");
        return transacTO;
    }

    @Override
    public TransacTO saveTransaction(TransactionRequest transactionRequest) throws TransactionNotFound {

        log.info("Inside save transactionImpl");
        Transaction transaction= new Transaction();
        transaction.setTransacAmount(transactionRequest.getTransacAmt());
        transaction.setTransacDate(transactionRequest.getTransacDate());
        transaction.setCustId(transactionRequest.getCustId());
        transaction.setTransacStatus(transactionRequest.getTransacStatus());

        Transaction trasacResp= transactionRepo.save(transaction);
        if(trasacResp==null){
            log.info("Transaction not saved");
            throw new TransactionNotFound("Transaction not found");
        }
        TransacTO transacTO= new TransacTO();
        transacTO.setTransacAmt(trasacResp.getTransacAmount());
        transacTO.setTransacDate(trasacResp.getTransacDate());
        transacTO.setCustId(trasacResp.getCustId());
        transacTO.setTransacStatus(trasacResp.getTransacStatus());

        log.info("End of save Transaction");
        return transacTO;
    }

    @Override
    public String deleteTransaction(int transacId) throws TransactionNotFound {

        Optional<Transaction> transactionOptional= transactionRepo.findById(transacId);
        if(transactionOptional==null){
            log.info("Transaction details does not exist");
            throw new TransactionNotFound("Transaction details does not exist");
        }
        transactionRepo.deleteById(transacId);
        log.info("Transaction is deleted by code:{}", transacId);
        return "Transaction is deleted by Id: "+ transacId;
    }
}
