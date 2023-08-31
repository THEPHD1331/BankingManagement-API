package com.BankingApplication.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanTO {

    private int loanId;
    private String loanType;
    private int loanAmt;
    private BranchTO branchTO;
    private CustomerTO customerTO;
}
