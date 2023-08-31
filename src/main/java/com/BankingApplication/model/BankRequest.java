package com.BankingApplication.model;

import lombok.Data;

import java.util.Set;

@Data
public class BankRequest {

    private int bankCode;
    private String bankName;
    private String bankAddress;
    private Set<BranchRequest> branchRequests;
}
