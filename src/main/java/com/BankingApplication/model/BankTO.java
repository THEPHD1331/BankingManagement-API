package com.BankingApplication.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BankTO {

    private String bankName;
    private String bankAddress;
    private List<BranchTO> branches;
}
