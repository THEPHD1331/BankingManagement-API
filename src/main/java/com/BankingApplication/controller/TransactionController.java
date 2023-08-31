package com.BankingApplication.controller;

import com.BankingApplication.exception.TransactionNotFound;
import com.BankingApplication.model.TransacTO;
import com.BankingApplication.model.TransactionRequest;
import com.BankingApplication.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TransacTO>> getAllTransactions(){
        log.info("Get Transactions method");
        List<TransacTO> transac= null;
        try{
            transac = transactionService.getAllTransactions();
            log.info("List of Transactions: Transaction:{}", transac);
        }
        catch (TransactionNotFound exp){
            log.error("Transaction details not found", exp);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of getTransac method");
        return new ResponseEntity<>(transac, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<TransacTO> getTransactionById(@RequestParam("transacId") int transacId){
        log.info("Get Transactions method");
        TransacTO transac= null;
        try{
            transac = transactionService.getTransactionById(transacId);
            log.info("Get Transaction by Id: Transaction:{}", transac);
        }
        catch (TransactionNotFound exp){
            log.error("Transaction details not found", exp);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of getTransacById method");
        return new ResponseEntity<>(transac, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TransacTO> saveTransaction(@RequestBody TransactionRequest transactionRequest){
        log.info("save Transactions method");
        if(transactionRequest==null){
            log.info("Invalid input:{}", transactionRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        TransacTO transac= null;
        try{
            transac = transactionService.saveTransaction(transactionRequest);
            log.info("save Transaction : Transaction:{}", transac);
        }
        catch (TransactionNotFound exp){
            log.error("Transaction details not Saved", exp);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of saveTransaction method");
        return new ResponseEntity<>(transac, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTransaction(@RequestParam("transacId") int trasacId){

     if(trasacId<=0) {
         log.info("Invalid Input");
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
     }
      String response;
        try{
            response = transactionService.deleteTransaction(trasacId);
            log.info("delete Transaction : Transaction:{}", response);
        }
        catch (TransactionNotFound exp){
            log.error("Transaction details not delete", exp);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of deleteTransaction method");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}


