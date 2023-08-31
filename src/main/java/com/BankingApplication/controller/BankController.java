package com.BankingApplication.controller;

import com.BankingApplication.exception.BankDetailsNotFound;
import com.BankingApplication.model.BankRequest;
import com.BankingApplication.model.BankTO;
import com.BankingApplication.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api/banks")
public class BankController {

    @Autowired
    BankService bankService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    ResponseEntity<List<BankTO>> getBanks(){
        log.info("Get Bank method");
        List<BankTO> bankto= null;
        try{
            bankto = bankService.getAllBanks();
           log.info("List of banks: bank:{}", bankto);
        }
        catch (BankDetailsNotFound exp){
            log.error("Bank details not found", exp);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of getBanks method");
        return new ResponseEntity<>(bankto, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<BankTO> getBankById(@PathVariable("code") int bankCode){
        log.info("Inside the BankControllerV1.getBankById");
        BankTO bankTO= null;

        try {
            bankTO = bankService.getBankByCode(bankCode);
            log.info("Bank details for the bank code, bank code:{}", bankCode);
        } catch (BankDetailsNotFound ex1){
            log.error("Bank details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while getting the bank details by bank code", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
            log.info("End of BankControllerV1.getBankById");

        return new ResponseEntity<>(bankTO, HttpStatus.OK);
    }

//    @GetMapping("/byName")
//    public ResponseEntity<BankTO> getBankByName(@RequestParam("bankname") String bankName){
//        log.info("Inside the BankController.getBankByName");
//        BankTO bankTO= null;
//        if(StringUtils.isEmpty(bankName)){
//            log.info("Invalid input");
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        try {
//            bankTO = bankService.getBankByName(bankName);
//            log.info("Bank details for the bank code, bank code:{}", bankName);
//        } catch (BankDetailsNotFound ex1){
//            log.error("Bank details not found", ex1);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }catch (Exception ex){
//            log.error("Exception while getting the bank details by bank code", ex);
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        log.info("End of BankController.getBankByName");
//        return new ResponseEntity<>(bankTO, HttpStatus.OK);
//    }
@GetMapping("/byName")
public ResponseEntity<BankTO> getBankByName(@RequestParam("bankname") String bankName) throws BankDetailsNotFound{
    log.info("Inside the BankController.getBankByName");
    if(StringUtils.isEmpty(bankName)){
        log.info("Invalid input");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    BankTO  bankTO = bankService.getBankByName(bankName);
        log.info("Bank details for the bank code, bank code:{}", bankName);

    log.info("End of BankController.getBankByName");
    return new ResponseEntity<>(bankTO, HttpStatus.OK);
}

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BankTO> saveBank(@RequestBody BankRequest bankRequest){
        log.info("Inside saveBank method");

        if(bankRequest == null){
            log.info("Invalid bank request , bankRequest:{}", bankRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
        BankTO bankTO;
        try {
            bankTO= bankService.saveBank(bankRequest);
            log.info("save bank response, bankRequest:{}", bankTO);
        } catch (BankDetailsNotFound ex1){
            log.error("Bank details not saved", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while saving the bank details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.saveBank");
        return new ResponseEntity<>(bankTO, HttpStatus.OK);

    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BankTO> updateBank(@RequestBody BankRequest bankRequest){
        log.info("Inside update Bank method");

        if(bankRequest == null){
            log.info("Invalid bank request , bankRequest:{}", bankRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BankTO bankTO;
        try {
            bankTO= bankService.updateBank(bankRequest);
            log.info("save bank response, bankRequest:{}", bankTO);
        } catch (BankDetailsNotFound ex1){
            log.error("Bank details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while updating the bank details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.updateBank");
        return new ResponseEntity<>(bankTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBankByCode(@RequestParam("bankCode") int bankCode){
    log.info("Inside delete bank by bank code");
        if(bankCode<=0){
            log.info("Invalid input!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String response;

        try{
            response= bankService.deleteBankByCode(bankCode);
            log.info("Delete bank response:{}", response);
        }catch (BankDetailsNotFound ex1){
            log.error("Bank details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception while deleting the bank details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.deleteBank");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/byName")
    public ResponseEntity<String> deleteBankByName(@RequestParam("bankName") String bankName){
        log.info("Inside delete bank by bank code");
        if(StringUtils.isEmpty(bankName)){
            log.info("Invalid input!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String response;
        try{
            response= bankService.deleteBankByName(bankName);
            log.info("Delete bank response:{}", response);
        }catch (BankDetailsNotFound ex1){
            log.error("Bank details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception while deleting the bank details by Name ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.deleteBankByName");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/byIdAndByName")
    public ResponseEntity<List<CompletableFuture<BankTO>>> getBankByIdAndByName
            (@RequestParam("bankCode") int bankCode, @RequestParam("bankName") String bankName) {

        log.info("Inside get Bank by Id and Name");
        CompletableFuture<BankTO> bank1= null;
        CompletableFuture<BankTO> bank2= null;
        List<CompletableFuture<BankTO>> bankTOList= new ArrayList<>();

        try{
        bank1= bankService.findBankByCode(bankCode);
        log.info("Getting bank by code:{}",bankCode);
        bank2= bankService.findBankByName(bankName);
        log.info("Getting bank by name:{}",bankName);

        CompletableFuture.allOf(bank1, bank2).join();
        bankTOList.add(bank1);
        bankTOList.add(bank2);

        if(CollectionUtils.isEmpty(bankTOList)){
            log.info("Bank details not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        }catch (Exception ex){
            log.info("An error occurred while getting bank details");
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.deleteBankByName");
        return new ResponseEntity<>(bankTOList, HttpStatus.OK);
    }
}