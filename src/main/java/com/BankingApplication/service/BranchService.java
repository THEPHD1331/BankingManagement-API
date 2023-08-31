package com.BankingApplication.service;

import com.BankingApplication.exception.BranchDetailsNotFound;
import com.BankingApplication.model.BranchRequest;
import com.BankingApplication.model.BranchTO;
import java.util.List;

public interface BranchService {

    BranchTO getBranchById(int branchCode) throws BranchDetailsNotFound;
    BranchTO getBranchByName(String branchName) throws BranchDetailsNotFound;
    List<BranchTO> getAllBranches() throws BranchDetailsNotFound;
    BranchTO saveBranch(BranchRequest branchRequest) throws BranchDetailsNotFound;
    BranchTO updateBranch(BranchRequest branchRequest) throws BranchDetailsNotFound;
    String deleteBranchById(int branchId) throws BranchDetailsNotFound;
    String deleteBranchByName(String branchName) throws BranchDetailsNotFound;

}
