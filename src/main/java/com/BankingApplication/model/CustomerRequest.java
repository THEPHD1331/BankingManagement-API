package com.BankingApplication.model;

import lombok.Data;

@Data
public class CustomerRequest {
    private int custId;
    private String custName;
    private String custAddress;
    private long custPhone;
    private int loanId;
    private int accNo;
}
