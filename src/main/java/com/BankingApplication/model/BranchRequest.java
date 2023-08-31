package com.BankingApplication.model;

import lombok.Data;

@Data
public class BranchRequest {

    private int branchId;
    private String branchName;
    private String branchAddress;
    private int bankCode;
}
