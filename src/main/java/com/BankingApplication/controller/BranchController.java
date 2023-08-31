package com.BankingApplication.controller;

import com.BankingApplication.exception.BranchDetailsNotFound;
import com.BankingApplication.model.BranchRequest;
import com.BankingApplication.model.BranchTO;
import com.BankingApplication.service.BranchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    BranchService branchService;

@GetMapping("/{code}")
    ResponseEntity<BranchTO> getBranchById(@PathVariable("code") int branchId) throws BranchDetailsNotFound {

    log.info("Enter Id to get the branch: branchId:{}", branchId);

    if(branchId<=0){ // Section for invalid branch Id
        log.info("Invalid branch Id");
        new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    BranchTO branchTO= null;

    try{
        branchTO= branchService.getBranchById(branchId); // get branch by Id
        log.info("Branch by input Id: branchTO:{}", branchTO);
    }catch (Exception exp){ // catches exception if any
      log.error("An Error occurred while getting branch details", exp);
      new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<BranchTO>(branchTO, HttpStatus.OK); // if no exp , returns OK
}

    @GetMapping
    ResponseEntity<List<BranchTO>> getBranches(){
        log.info("Get Branches method");
        List<BranchTO> branchto= null;
        try{
            branchto = branchService.getAllBranches();
            log.info("List of branches: branches:{}", branchto);
        }
        catch (BranchDetailsNotFound exp){
            log.error("Branch details not found", exp);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("End of getBanks method");
        return new ResponseEntity<>(branchto, HttpStatus.OK);
    }

//    @GetMapping("/byName")
//    public ResponseEntity<BranchTO> getBranchByName(@RequestParam("branchname") String branchName) throws BranchDetailsNotFound{
//        log.info("Enter the name of branch, branchName:{}", branchName);
//
//        if(StringUtils.isEmpty(branchName)){
//            log.info("Invalid Input");
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        BranchTO branchTO= null;
//        try {
//             branchTO = branchService.getBranchByName(branchName);
//            log.info("Get Branch by Name response, branchDTO:{}", branchTO);
//        } catch (BranchDetailsNotFound exp){
//            log.error("Branch details not found", exp);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//        catch (Exception ex){
//            log.error("Exception");
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return new ResponseEntity<BranchTO>(branchTO, HttpStatus.OK);
//    }
@GetMapping("/byName")
public ResponseEntity<BranchTO> getBranchByName(@RequestParam("branchname") String branchName) throws BranchDetailsNotFound{
    log.info("Enter the name of branch, branchName:{}", branchName);

    if(StringUtils.isEmpty(branchName)){
        log.info("Invalid Input");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    BranchTO branchTO= null;
        branchTO = branchService.getBranchByName(branchName);
        log.info("Get Branch by Name response, branchDTO:{}", branchTO);

    return new ResponseEntity<>(branchTO, HttpStatus.OK);
}

    @PostMapping
    public ResponseEntity<BranchTO> saveBranch(@RequestBody BranchRequest branchRequest){
    log.info("Inside save branch method:{}", branchRequest);

        if(branchRequest == null){
            log.info("Invalid branch request , branchRequest:{}", branchRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BranchTO branchTO;
        try {
            branchTO= branchService.saveBranch(branchRequest);
            log.info("save branch response, branchRequest:{}", branchTO);
        } catch (BranchDetailsNotFound ex1){
            log.error("Branch details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while saving the branch details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BranchController.saveBranch");
        return new ResponseEntity<>(branchTO, HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<BranchTO> updateBranch(@RequestBody BranchRequest branchRequest){
        log.info("Inside update branch method:{}", branchRequest);

        if(branchRequest == null){
            log.info("Invalid branch request , branchRequest:{}", branchRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        BranchTO branchTO;
        try {
            branchTO= branchService.updateBranch(branchRequest);
            log.info("update branch response, branchRequest:{}", branchTO);
        } catch (BranchDetailsNotFound ex1){
            log.error("Branch details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while updating the branch details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BranchController.updateBranch");
        return new ResponseEntity<>(branchTO, HttpStatus.OK);
    }


    @DeleteMapping
    public ResponseEntity<String> deleteBranchById(@RequestParam("branchId") int branchId){

    if(branchId<=0){
        log.info("Invalid Input");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
        String response;
        try{
            response= branchService.deleteBranchById(branchId);
            log.info("Delete branch response:{}", response);
        }catch (BranchDetailsNotFound ex1){
            log.error("Branch details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception while deleting the branch details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.deleteBranch");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/byName")
    public ResponseEntity<String> deleteBranchByName(@RequestParam("branchName") String branchName){

        if(branchName==null){
            log.info("Invalid Input");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String response;
        try{
            response= branchService.deleteBranchByName(branchName);
            log.info("Delete branch response:{}", response);
        }catch (BranchDetailsNotFound ex1){
            log.error("Branch details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.error("Exception while deleting the branch details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of BankController.deleteBranch");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
