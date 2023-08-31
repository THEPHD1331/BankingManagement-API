package com.BankingApplication.service;

import com.BankingApplication.entity.Bank;
import com.BankingApplication.entity.Branch;
import com.BankingApplication.exception.BranchDetailsNotFound;
import com.BankingApplication.model.BankTO;
import com.BankingApplication.model.BranchRequest;
import com.BankingApplication.model.BranchTO;
import com.BankingApplication.repository.BranchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService{

    @Autowired
    BranchRepository branchRepo;

    @Override
    public List<BranchTO> getAllBranches() throws BranchDetailsNotFound {

        List<Branch> branch= branchRepo.findAll();
        log.info("List of branches: branches:{}", branch);

        if(CollectionUtils.isEmpty(branch)){
            log.info("Branch details not found!");
            throw new BranchDetailsNotFound("Branch details not found");
        }
        List<BranchTO> branchStream= branch.stream().map(branch1 -> {

            BranchTO branchTO= new BranchTO();
            branchTO.setBranchName(branch1.getBranchName());
            branchTO.setBranchAddress(branch1.getBranchAddress());

            BankTO bankTO= new BankTO();
            bankTO.setBankName(branch1.getBranchName());
            bankTO.setBankAddress(branch1.getBranchAddress());

            return branchTO;
        }).collect(Collectors.toList());
        return branchStream;
    }

    @Override
    public BranchTO getBranchById(int branchCode) throws BranchDetailsNotFound {
        log.info("Enter Id to get branch, branchCode:{}", branchCode );

       Optional<Branch> branch= branchRepo.findById(branchCode);

        if(!branch.isPresent()){
            log.info("Branch not found!");
            throw new BranchDetailsNotFound("Branch not found for the input Id");
        }
      Branch b1= branch.get();
        BranchTO branchTO= new BranchTO();
        branchTO.setBranchName(b1.getBranchName());
        branchTO.setBranchAddress(b1.getBranchAddress());

        return branchTO;
    }

    @Override
    public BranchTO getBranchByName(String branchName) throws BranchDetailsNotFound {
        log.info("Enter name to get branch, branchName:{}", branchName );

        Optional<Branch> branch= branchRepo.findByBranchName(branchName);

        if(!branch.isPresent()){
            log.info("Branch not found!");
            throw new BranchDetailsNotFound("Branch not found for the input name");
        }
        Branch b1= branch.get();
        BranchTO branchTO= new BranchTO();
        branchTO.setBranchName(b1.getBranchName());
        branchTO.setBranchAddress(b1.getBranchAddress());

        return branchTO;
    }

    @Override
    public BranchTO saveBranch(BranchRequest branchRequest) throws BranchDetailsNotFound {

        log.info("Inside the branch request , branchRequest:{}", branchRequest);

        Branch branchResponse = new Branch();
        branchResponse.setBranchName(branchRequest.getBranchName());
        branchResponse.setBranchAddress(branchRequest.getBranchAddress());
        Bank ban = new Bank();
        ban.setBankCode(branchRequest.getBankCode());
        branchResponse.setBankCode(ban);


        Branch newBranch = branchRepo.save(branchResponse);

        if (newBranch == null) {
            log.info("Branch Details Not Found");
            throw new BranchDetailsNotFound();
        }
        BranchTO branchTO3 = new BranchTO();
        branchTO3.setBranchName(newBranch.getBranchName());
        branchTO3.setBranchAddress(newBranch.getBranchAddress());

        log.info("End of BankServiceImpl.saveBranch");
        return branchTO3;
    }

    @Override
    public BranchTO updateBranch(BranchRequest branchRequest) throws BranchDetailsNotFound {
        log.info("Inside the update branch request , branchRequest:{}", branchRequest);

        Branch branchResponse = new Branch();
        branchResponse.setBranchId(branchRequest.getBranchId());
        branchResponse.setBranchName(branchRequest.getBranchName());
        branchResponse.setBranchAddress(branchRequest.getBranchAddress());
        Bank ban = new Bank();
        ban.setBankCode(branchRequest.getBankCode());
        branchResponse.setBankCode(ban);


        Branch newBranch = branchRepo.save(branchResponse);

        if (newBranch == null) {
            log.info("Branch Details Not Found");
            throw new BranchDetailsNotFound();
        }
        BranchTO branchTO = new BranchTO();

        branchTO.setBranchName(newBranch.getBranchName());
        branchTO.setBranchAddress(newBranch.getBranchAddress());

        log.info("End of BankServiceImpl.UpdateBranch");
        return branchTO;
    }

    @Override
    public String deleteBranchById(int branchId) throws BranchDetailsNotFound {
        Optional<Branch> branchOp= branchRepo.findById(branchId);
        if(branchOp==null){
            log.info("Branch Details does not exist");
            throw new BranchDetailsNotFound("Branch details does not exist");
        }
        branchRepo.deleteById(branchId);
        log.info("Branch is deleted by code:{}", branchId);
        return "Branch is deleted by branch code: "+ branchId;
    }

    @Transactional
    @Override
    public String deleteBranchByName(String branchName) throws BranchDetailsNotFound {
        Optional<Branch> branchOp= branchRepo.findByBranchName(branchName);
        if(branchOp==null){
            log.info("Branch Details does not exist");
            throw new BranchDetailsNotFound("Branch details does not exist");
        }
        branchRepo.deleteByBranchName(branchName);
        log.info("Branch is deleted by name:{}", branchName);
        return "Branch is deleted by branch name: "+ branchName;
    }
}
