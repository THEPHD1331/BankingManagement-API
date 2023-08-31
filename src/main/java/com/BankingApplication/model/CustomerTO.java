package com.BankingApplication.model;

import lombok.Data;

import java.util.List;

@Data
public class CustomerTO {

    private int custId;
    private String custName;
    private String custAddress;
    private long custPhone;
    List<AccountTO> accounts;
    List<LoanTO> loans;
}
