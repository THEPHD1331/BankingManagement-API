package com.BankingApplication.model;

import lombok.Data;

@Data
public class AccountRequest {
    private int accNo;
    private String accType;
    private int accBalance;
    private int branchId;
    private int customerId;

}
