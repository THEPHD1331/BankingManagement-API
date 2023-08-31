package com.BankingApplication.model;

import com.BankingApplication.entity.Branch;
import com.BankingApplication.entity.Customer;
import lombok.Data;

@Data
public class AccountTO {

    private int accNo;
    private String accType;
    private int accBalance;
    private Branch branchId;
    private Customer customerId;

}
