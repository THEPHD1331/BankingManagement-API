package com.BankingApplication.service;

import com.BankingApplication.exception.CustomerNotFound;
import com.BankingApplication.model.CustomerRequest;
import com.BankingApplication.model.CustomerTO;

import java.util.List;

public interface CustomerService {

    List<CustomerTO> getAllCustomers() throws CustomerNotFound;
    CustomerTO getCustomerById(int custId) throws CustomerNotFound;
    CustomerTO getCustomerByName(String custName) throws CustomerNotFound;
    CustomerTO saveCustomer(CustomerRequest customerRequest) throws CustomerNotFound;
    CustomerTO updateCustomer(CustomerRequest customerRequest) throws CustomerNotFound;
    String deleteCustomerById(int custId) throws CustomerNotFound;
    String deleteCustomerByName(String custName) throws CustomerNotFound;
}
