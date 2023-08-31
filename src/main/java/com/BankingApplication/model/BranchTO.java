package com.BankingApplication.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BranchTO {

    private String branchName;
    private String branchAddress;
    private BankTO bank;
}
