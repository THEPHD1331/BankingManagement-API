package com.BankingApplication.model;

import com.BankingApplication.entity.Branch;
import com.BankingApplication.entity.Customer;
import lombok.Data;

@Data
public class LoanRequest {

    private int loanId;
    private String loanType;
    private int lonaAmt;
    private int branchId;
    private int customerId;
}
