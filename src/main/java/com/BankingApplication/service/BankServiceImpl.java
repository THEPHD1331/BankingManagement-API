package com.BankingApplication.service;

import com.BankingApplication.entity.Bank;
import com.BankingApplication.entity.Branch;
import com.BankingApplication.exception.BankDetailsNotFound;
import com.BankingApplication.model.BankRequest;
import com.BankingApplication.model.BankTO;
import com.BankingApplication.model.BranchTO;
import com.BankingApplication.repository.BankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    BankRepository bankRepo;

    @Override
    public List<BankTO> getAllBanks() throws BankDetailsNotFound {
        log.info("Inside get All banks method");

        List<Bank> banks = bankRepo.findAll();
        log.info("List of banks: banks:{}", banks);

        if (CollectionUtils.isEmpty(banks)) {
            log.info("Bank details not found!");
            throw new BankDetailsNotFound("Bank details not found");
        }

        List<BankTO> bankStream = banks.stream().map(bank -> {
            BankTO bankTO = new BankTO();
            bankTO.setBankName(bank.getBankName());
            bankTO.setBankAddress(bank.getBankAddress());
            Set<Branch> branches = bank.getBranchSet();

            if (!CollectionUtils.isEmpty(branches)) {
                List<BranchTO> branchStream = branches.stream().map(branch -> {
                    BranchTO branchTO = new BranchTO();
                    branchTO.setBranchName(branch.getBranchName());
                    branchTO.setBranchAddress(branch.getBranchAddress());
                    return branchTO;
                }).collect(Collectors.toList());
                bankTO.setBranches(branchStream);
            }
            return bankTO;
        }).collect(Collectors.toList());
        return bankStream;
    }

    @Cacheable("byCode")
    @Override
    public BankTO getBankByCode(int bankCode) throws BankDetailsNotFound {
        log.info("Inside the getBankByCode method, bankCode:{}", bankCode);
        Optional<Bank> bank = bankRepo.findById(bankCode);

        if (!bank.isPresent()) {
            log.info("Bank details not found for the bank code:{}", bankCode);
            throw new BankDetailsNotFound("Bank details not found");
        }
        log.info("Bank details for the bank code, bankCode:{}", bank.get());
        Bank bank1 = bank.get();
        BankTO bankTO = new BankTO();
        bankTO.setBankName(bank1.getBankName());
        bankTO.setBankAddress(bank1.getBankAddress());
        Set<Branch> branches = bank1.getBranchSet();

        if (!CollectionUtils.isEmpty(branches)) {
            List<BranchTO> branchTOS = branches.stream().map(branch -> {
                BranchTO branchTO = new BranchTO();
                branchTO.setBranchName(branch.getBranchName());
                branchTO.setBranchAddress(branch.getBranchAddress());
                return branchTO;
            }).collect(Collectors.toList());
            bankTO.setBranches(branchTOS);
        }
        log.info("End of getBankByCode method");
        return bankTO;
    }

    @Override
    public BankTO getBankByName(String bankName) throws BankDetailsNotFound {
        log.info("Inside the BankServiceImpl.getBankByName, bankName:{}", bankName);
        Optional<Bank> bank = bankRepo.findByBankName(bankName);

        if (!bank.isPresent()) {
            log.info("Bank details not found for the bank name:{}", bankName);
            throw new BankDetailsNotFound("Bank details not found");
        }
        log.info("Bank details for the bank name, bankName:{}", bank.get());
        Bank bank1 = bank.get();
        BankTO bankTO = new BankTO();
        bankTO.setBankName(bank1.getBankName());
        bankTO.setBankAddress(bank1.getBankAddress());
        Set<Branch> branches = bank1.getBranchSet();

        if (!CollectionUtils.isEmpty(branches)) {
            List<BranchTO> branchTOS = branches.stream().map(branch -> {
                BranchTO branchTO = new BranchTO();
                branchTO.setBranchName(branch.getBranchName());
                branchTO.setBranchAddress(branch.getBranchAddress());
                return branchTO;
            }).collect(Collectors.toList());

            bankTO.setBranches(branchTOS);
        }
        log.info("End of BankServiceImpl.getBankByName");
        return bankTO;
    }

    @Override
    public BankTO saveBank(BankRequest bankRequest) throws BankDetailsNotFound {
        log.info("Inside insert Bank method:{} ", bankRequest);

        Bank bank = new Bank();
        bank.setBankName(bankRequest.getBankName());
        bank.setBankAddress(bankRequest.getBankAddress());

        Bank bankResp = bankRepo.save(bank);
        if (bankResp == null) {
            log.info("Bank is not saved");
            throw new BankDetailsNotFound("Bank details are not saved");
        }
        BankTO bankTO = new BankTO();
        bankTO.setBankName(bankResp.getBankName());
        bankTO.setBankAddress(bankResp.getBankAddress());
        Set<Branch> branches = bankResp.getBranchSet();

        if (!CollectionUtils.isEmpty(branches)) {
            List<BranchTO> branchTOS = branches.stream().map(branch -> {
                BranchTO branchTO = new BranchTO();
                branchTO.setBranchName(branch.getBranchName());
                branchTO.setBranchAddress(branch.getBranchAddress());
                return branchTO;
            }).collect(Collectors.toList());

            bankTO.setBranches(branchTOS);
        }
        log.info("End of save bank service");
        return bankTO;
    }

    @Override
    public BankTO updateBank(BankRequest bankRequest) throws BankDetailsNotFound {
        log.info("Inside insert Bank method:{} ", bankRequest);

        Bank bank = new Bank();
        bank.setBankCode(bankRequest.getBankCode());
        bank.setBankName(bankRequest.getBankName());
        bank.setBankAddress(bankRequest.getBankAddress());

        Bank bankResp = bankRepo.save(bank);
        if (bankResp == null) {
            log.info("Bank is not updated");
            throw new BankDetailsNotFound("Bank details are not updated");
        }
        BankTO bankTO = new BankTO();
        bankTO.setBankName(bankResp.getBankName());
        bankTO.setBankAddress(bankResp.getBankAddress());
        Set<Branch> branches = bankResp.getBranchSet();

        if (!CollectionUtils.isEmpty(branches)) {
            List<BranchTO> branchTOS = branches.stream().map(branch -> {
                BranchTO branchTO = new BranchTO();
                branchTO.setBranchName(branch.getBranchName());
                branchTO.setBranchAddress(branch.getBranchAddress());
                return branchTO;
            }).collect(Collectors.toList());

            bankTO.setBranches(branchTOS);
        }
        log.info("End of save bank service");
        return bankTO;
    }

    @Override
    public String deleteBankByCode(int bankCode) throws BankDetailsNotFound {

        Optional<Bank> bankOp = bankRepo.findById(bankCode);
        if (bankOp == null) {
            log.info("Bank Details does not exist");
            throw new BankDetailsNotFound("Bank details does not exist");
        }
        bankRepo.deleteById(bankCode);
        log.info("Bank is deleted by code:{}", bankCode);
        return "Bank is deleted by bank code: " + bankCode;
    }

    @Transactional
    @Override
    public String deleteBankByName(String bankName) throws BankDetailsNotFound {
        Optional<Bank> bankOp = bankRepo.findByBankName(bankName);
        if (bankOp == null) {
            log.info("Bank Details does not exist");
            throw new BankDetailsNotFound("Bank details does not exist");
        }
        bankRepo.deleteByBankName(bankName);
        log.info("Bank is deleted by Name:{}", bankName);
        return "Bank is deleted by bank Name: " + bankName;
    }

    @Async("async")
    @Override
    public CompletableFuture<BankTO> findBankByCode(int bankCode) throws InterruptedException, BankDetailsNotFound {
        log.info("Inside BankServiceImpl.findById and id: {}", bankCode);
        Thread.sleep(6000l);
        BankTO bankTO = null;
        if (bankCode < 0) {
            log.info("Invalid Bank id:{}", bankCode);
            throw new BankDetailsNotFound("Bank id is not valid");
        }
        Optional<Bank> bankOptional = bankRepo.findById(bankCode);

        if (bankOptional.isPresent()) {
            log.info("Bank details for the id: {} and the values :{}", bankCode, bankOptional.get());
            bankTO = new BankTO();
            bankTO.setBankName(bankOptional.get().getBankName());
            bankTO.setBankAddress(bankOptional.get().getBankAddress());
            Set<Branch> branches = bankOptional.get().getBranchSet();

            if (!CollectionUtils.isEmpty(branches)) {
                List<BranchTO> branchTOS = branches.stream().map(branch -> {
                    BranchTO branchTO = new BranchTO();
                    branchTO.setBranchName(branch.getBranchName());
                    branchTO.setBranchAddress(branch.getBranchAddress());
                    return branchTO;
                }).collect(Collectors.toList());
                bankTO.setBranches(branchTOS);
            }}
        return CompletableFuture.completedFuture(bankTO);
    }

    @Async("async")
    @Override
        public CompletableFuture<BankTO> findBankByName (String bankName) throws BankDetailsNotFound, InterruptedException {
            log.info("Inside BankServiceImpl.findById and id: {}", bankName);
            Thread.sleep(6000l);
            BankTO bankTO = null;
            if (bankName == null) {
                log.info("Invalid bank Name:{}", bankName);
                throw new BankDetailsNotFound("Bank id is not valid");
            }
            Optional<Bank> bankOptional = bankRepo.findByBankName(bankName);

            if (bankOptional.isPresent()) {
                log.info("Bank details for the id: {} and the values :{}", bankName, bankOptional.get());
                bankTO = new BankTO();
                bankTO.setBankName(bankOptional.get().getBankName());
                bankTO.setBankAddress(bankOptional.get().getBankAddress());
                Set<Branch> branches = bankOptional.get().getBranchSet();

                if (!CollectionUtils.isEmpty(branches)) {
                    List<BranchTO> branchTOS = branches.stream().map(branch -> {
                        BranchTO branchTO = new BranchTO();
                        branchTO.setBranchName(branch.getBranchName());
                        branchTO.setBranchAddress(branch.getBranchAddress());
                        return branchTO;
                    }).collect(Collectors.toList());
                    bankTO.setBranches(branchTOS);
                }}
            return CompletableFuture.completedFuture(bankTO);
        }
    }