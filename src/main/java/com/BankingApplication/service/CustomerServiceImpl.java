package com.BankingApplication.service;

import com.BankingApplication.entity.Account;
import com.BankingApplication.entity.Customer;
import com.BankingApplication.entity.Loan;
import com.BankingApplication.exception.CustomerNotFound;
import com.BankingApplication.model.AccountTO;
import com.BankingApplication.model.CustomerRequest;
import com.BankingApplication.model.CustomerTO;
import com.BankingApplication.model.LoanTO;
import com.BankingApplication.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    CustomerRepository customerRepo;

    @Override
    public List<CustomerTO> getAllCustomers() throws CustomerNotFound {

            List<Customer> custList= customerRepo.findAll();
            log.info("All Accounts: {}", custList);

            if(CollectionUtils.isEmpty(custList)){
                log.info("Customer details not found");
                throw new CustomerNotFound("Customer Details not found");
            }

            List<CustomerTO> custTOList= custList.stream().map(customers -> {

                CustomerTO custTO= new CustomerTO();
                custTO.setCustId(customers.getCustId());
                custTO.setCustName(customers.getCustName());
                custTO.setCustAddress(customers.getCustAddress());
                custTO.setCustPhone(customers.getCustPhone());

                Set<Account> accountSet= customers.getAccountSet();
                if(!CollectionUtils.isEmpty(accountSet)) {
                    List<AccountTO> accountTOS = accountSet.stream().map(accounts -> {
                        AccountTO accountTO = new AccountTO();
                        accountTO.setAccType(accounts.getAccountType());
                        accountTO.setAccBalance(accounts.getAccountBalance());
                        return accountTO;
                    }).collect(Collectors.toList());
                    custTO.setAccounts(accountTOS);
                }

                Set<Loan> loanSet= customers.getLoanSet();
                if(!CollectionUtils.isEmpty(loanSet)){
                    List<LoanTO> loanTOS= loanSet.stream().map(loans -> {
                        LoanTO loanTO = new LoanTO();
                        loanTO.setLoanType(loans.getLoanType());
                        loanTO.setLoanAmt(loans.getLoanAmount());
                        return loanTO;
                    }).collect(Collectors.toList());
                    custTO.setLoans(loanTOS);
                }
                return custTO;
            }).collect(Collectors.toList());

            log.info("---End of Accounts List---");
            return custTOList;
        }

    @Override
    public CustomerTO getCustomerById(int custId) throws CustomerNotFound {
        log.info("Inside get Customer by Cust Id method, CustId:{}", custId);

        Optional<Customer> customer= customerRepo.findById(custId);

        if(!customer.isPresent()){
            log.info("Customer details not found");
            throw new CustomerNotFound("Customer details not found!");
        }
       Customer cust= customer.get();
        CustomerTO customerTO= new CustomerTO();
        customerTO.setCustId(cust.getCustId());
        customerTO.setCustName(cust.getCustName());
        customerTO.setCustAddress(cust.getCustAddress());
        customerTO.setCustPhone(cust.getCustPhone());

        return customerTO;
    }

    @Override
    public CustomerTO getCustomerByName(String custName) throws CustomerNotFound {
        log.info("Inside get Customer by Cust name method, CustName:{}", custName);

        Optional<Customer> customer= customerRepo.findByCustName(custName);

        if(!customer.isPresent()){
            log.info("Customer details not found");
            throw new CustomerNotFound("Customer details not found!");
        }
        Customer cust= customer.get();
        CustomerTO customerTO= new CustomerTO();
        customerTO.setCustId(cust.getCustId());
        customerTO.setCustName(cust.getCustName());
        customerTO.setCustAddress(cust.getCustAddress());
        customerTO.setCustPhone(cust.getCustPhone());

        return customerTO;
    }

    @Override
    public CustomerTO saveCustomer(CustomerRequest customerRequest) throws CustomerNotFound {

           Customer customer= new Customer();
           customer.setCustId(customerRequest.getCustId());
           customer.setCustName(customerRequest.getCustName());
           customer.setCustAddress(customerRequest.getCustAddress());
           customer.setCustPhone(customerRequest.getCustPhone());

           Customer custResp= customerRepo.save(customer);
           if(custResp==null){
               log.info("Customer is not saved!");
               throw new CustomerNotFound("Customer is not saved");
           }
           CustomerTO customerTO= new CustomerTO();
           customerTO.setCustId(custResp.getCustId());
           customerTO.setCustName(custResp.getCustName());
           customerTO.setCustAddress(custResp.getCustAddress());
           customerTO.setCustPhone(custResp.getCustPhone());

        log.info("End of customer save");
        return customerTO;

    }

    @Override
    public CustomerTO updateCustomer(CustomerRequest customerRequest) throws CustomerNotFound {
        Customer customer= new Customer();
        customer.setCustId(customerRequest.getCustId());
        customer.setCustName(customerRequest.getCustName());
        customer.setCustAddress(customerRequest.getCustAddress());
        customer.setCustPhone(customerRequest.getCustPhone());

        Customer custResp= customerRepo.save(customer);
        if(custResp==null){
            log.info("Customer is not updated!");
            throw new CustomerNotFound("Customer is not updated");
        }
        CustomerTO customerTO= new CustomerTO();
        customerTO.setCustId(custResp.getCustId());
        customerTO.setCustName(custResp.getCustName());
        customerTO.setCustAddress(custResp.getCustAddress());
        customerTO.setCustPhone(custResp.getCustPhone());

        log.info("End of customer update");
        return customerTO;
    }

    @Override
    public String deleteCustomerById(int custId) throws CustomerNotFound {

        Optional<Customer> customerOp= customerRepo.findById(custId);
        if(customerOp==null){
            log.info("Customer Details does not exist");
            throw new CustomerNotFound("Customer details does not exist");
        }
        customerRepo.deleteById(custId);
        log.info("Customer is deleted by code:{}", custId);
        return "Customer is deleted by Customer Id: "+ custId;
    }

    @Transactional
    @Override
    public String deleteCustomerByName(String custName) throws CustomerNotFound {
        Optional<Customer> customerOp= customerRepo.findByCustName(custName);
        if(customerOp==null){
            log.info("Customer Details does not exist");
            throw new CustomerNotFound("Customer details does not exist");
        }
        customerRepo.deleteByCustName(custName);
        log.info("Customer is deleted by name:{}", custName);
        return "Customer is deleted by Customer Name: "+ custName;
    }
}

