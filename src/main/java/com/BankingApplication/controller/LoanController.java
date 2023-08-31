package com.BankingApplication.controller;

import com.BankingApplication.exception.LoanDetailsNotFound;
import com.BankingApplication.model.LoanRequest;
import com.BankingApplication.model.LoanTO;
import com.BankingApplication.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    LoanService loanService;

    @GetMapping
    public ResponseEntity<List<LoanTO>> getAllLoans(){
        List<LoanTO> loanListTO= null;
        try{
            loanListTO= loanService.getAllLoans();
            log.info("Loans List: {}", loanListTO);
        }
        catch (LoanDetailsNotFound expa){
            log.error("Loan not found", expa);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception exp){
            log.error("An Error occurred while getting the Loan details", exp);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(loanListTO, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<LoanTO> getLoanById(@PathVariable("code") int loanId){
        log.info("Inside the BankControllerV1.getBankById");
        LoanTO loanTO= null;

        try {
            loanTO = loanService.getLoanById(loanId);
            log.info("Loan details for the Loan Id, loanId:{}", loanId);
        } catch (LoanDetailsNotFound ex1){
            log.error("Loan details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while getting the Loan details by Loan Id", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of Get Loan by Loan Id");

        return new ResponseEntity<>(loanTO, HttpStatus.OK);
    }

    @GetMapping("/byType")
    public ResponseEntity<LoanTO> getLoanByType(@RequestParam("loanType") String loanType){
        log.info("Inside the Loan get by name");
        LoanTO loanTO= null;

        try {
            loanTO = loanService.getLoanByType(loanType);
            log.info("Loan details for the Loan type, loanType:{}", loanType);
        } catch (LoanDetailsNotFound ex1){
            log.error("Loan details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while getting the Loan details by Loan Type", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of Get Loan by Loan type");

        return new ResponseEntity<>(loanTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<LoanTO> saveLoan(@RequestBody LoanRequest loanRequest){
        log.info("Inside save loan ");

        if(loanRequest==null){
            log.info("Invalid Loan request, loanRequest:{}", loanRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LoanTO loanTO;
        try {
            loanTO = loanService.saveLoan(loanRequest);
            log.info("Loan details to save the Loan, loanType:{}", loanRequest);
        } catch (LoanDetailsNotFound ex1){
            log.error("Loan details not saved", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while saving the Loan details by Loan Type", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of save Loan");

        return new ResponseEntity<>(loanTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<LoanTO> updateLoan(@RequestBody LoanRequest loanRequest){
        log.info("Inside update loan ");

        if(loanRequest==null){
            log.info("Invalid Loan request, loanRequest:{}", loanRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        LoanTO loanTO;
        try {
            loanTO = loanService.updateLoan(loanRequest);
            log.info("Loan details to update the Loan, loanType:{}", loanRequest);
        } catch (LoanDetailsNotFound ex1){
            log.error("Loan details not updates", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while updating the Loan details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of update Loan");

        return new ResponseEntity<>(loanTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteLoanById(@RequestParam("loanId") int loanId){
        log.info("Inside delete loan ");

        if(loanId<=0){
            log.info("Invalid Loan request, loanRequest:{}", loanId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String resp;
        try {
            resp = loanService.deleteLoanById(loanId);
            log.info("Loan details to delete the Loan, loanType:{}", loanId);
        } catch (LoanDetailsNotFound ex1){
            log.error("Loan details not deleted", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while deleting the Loan details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of delete Loan");

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/byName")
    public ResponseEntity<String> deleteLoanByType(@RequestParam("loanType") String loanType){
        log.info("Inside delete loan ");

        if(loanType==null){
            log.info("Invalid Loan request, loanRequest:{}", loanType);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String resp;
        try {
            resp = loanService.deleteLoanByType(loanType);
            log.info("Loan details to delete the Loan, loanType:{}", loanType);
        } catch (LoanDetailsNotFound ex1){
            log.error("Loan details not deleted by type", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while deleting the Loan details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of delete Loan by type");

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
 }
