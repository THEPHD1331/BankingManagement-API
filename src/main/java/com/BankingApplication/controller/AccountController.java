package com.BankingApplication.controller;

import com.BankingApplication.exception.AccountDetailsNotFound;
import com.BankingApplication.model.AccountRequest;
import com.BankingApplication.model.AccountTO;
import com.BankingApplication.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountTO>> getAllAccounts(){
       List<AccountTO> accListTO= null;

        try{
            accListTO= accountService.findAllAccounts();
            log.info("Accounts List: {}", accListTO);
        }
        catch (AccountDetailsNotFound expa){
              log.error("Account not found", expa);
              return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception exp){
          log.error("An Error occurred while getting the account details", exp);
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return new ResponseEntity<>(accListTO, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<AccountTO> getAccountById(@PathVariable("code") int AccNo){
        log.info("Inside the BankControllerV1.getBankById");
       AccountTO accountTO= null;

        try {
            accountTO = accountService.getAccByID(AccNo);
            log.info("Account details for the Account No,Acc no:{}", AccNo);
        } catch (AccountDetailsNotFound ex1){
            log.error("Account details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while getting the Account details by Acc no", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of Get Account by Acc No");

        return new ResponseEntity<>(accountTO, HttpStatus.OK);
    }

    @GetMapping("/byType")
    public ResponseEntity<AccountTO> getAccountByType(@RequestParam("accountType") String accountType){
        log.info("Inside the BankController.getAccountByType");
        AccountTO accountTO= null;

        try {
            accountTO = accountService.getAccByType(accountType);
            log.info("Account details for the Account type,Acc type:{}", accountType);
        } catch (AccountDetailsNotFound ex1){
            log.error("Account details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while getting the Account details by Acc type", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of Get Account by Acc type");

        return new ResponseEntity<>(accountTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AccountTO> saveAccount(@RequestBody AccountRequest accountRequest){
        log.info("Inside the BankController.saveAccount");
        AccountTO accountTO= null;

        try {
            accountTO = accountService.saveAccount(accountRequest);
            log.info("Saving Account,Acc details:{}", accountRequest);
        } catch (AccountDetailsNotFound ex1){
            log.error("Account details not saved", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while saving the Account details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of save Account ");

        return new ResponseEntity<>(accountTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<AccountTO> updateAccount(@RequestBody AccountRequest accountRequest){
        log.info("Inside the BankController.updateAccount");
        AccountTO accountTO= null;

        try {
            accountTO = accountService.updateAccount(accountRequest);
            log.info("Updating Account,Acc details:{}", accountRequest);
        } catch (AccountDetailsNotFound ex1){
            log.error("Account details not updated", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while updating the Account details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of update Account ");

        return new ResponseEntity<>(accountTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAccountByNo(@RequestParam("accountNo") int accountNo){
        log.info("Inside the BankController.deleteAccount");
        String resp;

        try {
            resp = accountService.deleteAccountByNo(accountNo);
            log.info("Deleting Account,Acc details:{}", accountNo);
        } catch (AccountDetailsNotFound ex1){
            log.error("Account details not deleted", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while deleted the Account details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of delete Account ");

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/byName")
    public ResponseEntity<String> deleteAccountByName(@RequestParam("accountType") String accountType){
        log.info("Inside the BankController.deleteAccount");
        String resp;

        try {
            resp = accountService.deleteAccountByType(accountType);
            log.info("Deleting Account by type,Acc details:{}", accountType);
        } catch (AccountDetailsNotFound ex1){
            log.error("Account details not deleted", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while deleted the Account details by type", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of delete Account by type ");

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
 }
