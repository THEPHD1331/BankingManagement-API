package com.BankingApplication.controller;

import com.BankingApplication.exception.CustomerNotFound;
import com.BankingApplication.model.CustomerRequest;
import com.BankingApplication.model.CustomerTO;
import com.BankingApplication.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerTO>> getAllCustomers(){
        List<CustomerTO> custListTO= null;

        try{
            custListTO= customerService.getAllCustomers();
            log.info("Customers List: {}", custListTO);
        }
        catch (CustomerNotFound expa){
            log.error("Customer not found", expa);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception exp){
            log.error("An Error occurred while getting the Customer details", exp);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(custListTO, HttpStatus.OK);
    }

    @GetMapping("/{code}")
    public ResponseEntity<CustomerTO> getCustomerById(@PathVariable("code") int custId) throws CustomerNotFound {

        CustomerTO customerTO=null;
        log.info("Inside get customer by Id , custId:{}", custId);
        try{
            customerTO= customerService.getCustomerById(custId);
        } catch (CustomerNotFound exc){
            log.info("Customer not found ", exc);
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.info("There was an error getting customer by Id ", ex);
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerTO, HttpStatus.OK);
    }

    @GetMapping("/byName")
    public ResponseEntity<CustomerTO> getCustomerByName(@RequestParam("custName") String custName) throws CustomerNotFound {

        CustomerTO customerTO= null;
        log.info("Inside get customer by name , custName:{}", custName);

        if(StringUtils.isEmpty(custName)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try{
            customerTO= customerService.getCustomerByName(custName);
        } catch (CustomerNotFound exc){
            log.info("Customer not found ", exc);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            log.info("There was an error getting customer by name ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(customerTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CustomerTO> saveCustomer(@RequestBody CustomerRequest customerRequest){
        log.info("Inside save customer method:{}", customerRequest);

        if(customerRequest == null){
            log.info("Invalid customer request , customerRequest:{}", customerRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CustomerTO customerTO;
        try {
            customerTO= customerService.saveCustomer(customerRequest);
            log.info("save customer response, customerRequest:{}", customerRequest);
        } catch (CustomerNotFound ex1){
            log.error("customer details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while saving the customer details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of CustomerController.saveCust");
        return new ResponseEntity<>(customerTO, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CustomerTO> updateCustomer(@RequestBody CustomerRequest customerRequest){
        log.info("Inside update customer method:{}", customerRequest);

        if(customerRequest == null){
            log.info("Invalid customer request , customerRequest:{}", customerRequest);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CustomerTO customerTO;
        try {
            customerTO= customerService.updateCustomer(customerRequest);
            log.info("Update customer response, customerRequest:{}", customerRequest);
        } catch (CustomerNotFound ex1){
            log.error("customer details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while updating the customer details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of CustomerController.updateCust");
        return new ResponseEntity<>(customerTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCustById(@RequestParam("custId") int custId){
        log.info("Inside delete customer method:{}", custId);

        if(custId<=0){
            log.info("Invalid customer request , customerRequest:{}", custId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String resp;
        try {
            resp= customerService.deleteCustomerById(custId);
            log.info("Delete customer response, customerRequest:{}", custId);
        } catch (CustomerNotFound ex1){
            log.error("customer details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while deleting the customer details ", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of CustomerController.deleteCust");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @DeleteMapping("/byName")
    public ResponseEntity<String> deleteCustByName(@RequestParam("custName") String custName){
        log.info("Inside delete customer by name method:{}", custName);

        if(custName==null){
            log.info("Invalid customer request , customerRequest:{}", custName);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String resp;
        try {
            resp= customerService.deleteCustomerByName(custName);
            log.info("Delete customer response, customerRequest:{}", custName);
        } catch (CustomerNotFound ex1){
            log.error("customer details not found", ex1);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.error("Exception while deleting the customer details by name", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("End of CustomerController.deleteCustByName");
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
