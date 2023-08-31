package com.BankingApplication.service;

import com.BankingApplication.entity.Account;
import com.BankingApplication.entity.Branch;
import com.BankingApplication.entity.Customer;
import com.BankingApplication.exception.AccountDetailsNotFound;
import com.BankingApplication.model.AccountRequest;
import com.BankingApplication.model.AccountTO;
import com.BankingApplication.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    AccountRepository accountRepo;

    @Override
    public List<AccountTO> findAllAccounts() throws AccountDetailsNotFound {

        List<Account> accList= accountRepo.findAll();
        log.info("All Accounts: {}", accList);

        if(CollectionUtils.isEmpty(accList)){
            log.info("Account details not found");
            throw new AccountDetailsNotFound("Account not found!");
        }

        List<AccountTO> accTOList= accList.stream().map(account -> {

            AccountTO accTO= new AccountTO();
            accTO.setAccNo(account.getAccountNo());
            accTO.setAccType(account.getAccountType());
            accTO.setAccBalance(account.getAccountBalance());

            return accTO;
        }).collect(Collectors.toList());

        log.info("---End of Accounts List---");
        return accTOList;
    }

    @Override
    public AccountTO getAccByID(int AccNo) throws AccountDetailsNotFound {
        log.info("Inside get Account by Acc no method, AccNo:{}", AccNo);

        Optional<Account> account= accountRepo.findById(AccNo);

        if(!account.isPresent()){
            log.info("Account details not found");
            throw new AccountDetailsNotFound("Account details not found!");
        }

        Account acc= account.get();
        AccountTO accountTO = new AccountTO();

        accountTO.setAccNo(acc.getAccountNo());
        accountTO.setAccType(acc.getAccountType());
        accountTO.setAccBalance(acc.getAccountBalance());

        return accountTO;
    }

    @Override
    public AccountTO getAccByType(String accountType) throws AccountDetailsNotFound {
        log.info("Inside get Account by Acc Name method, AccName:{}", accountType);

        Optional<Account> account= accountRepo.findByAccountType(accountType);

        if(!account.isPresent()){
            log.info("Account details not found");
            throw new AccountDetailsNotFound("Account details not found!");
        }

        Account acc= account.get();
        AccountTO accountTO = new AccountTO();

        accountTO.setAccNo(acc.getAccountNo());
        accountTO.setAccType(acc.getAccountType());
        accountTO.setAccBalance(acc.getAccountBalance());

        return accountTO;
    }

    @Override
    public AccountTO saveAccount(AccountRequest accountRequest) throws AccountDetailsNotFound {

        Account account= new Account();
        account.setAccountNo(accountRequest.getAccNo());
        account.setAccountType(accountRequest.getAccType());
        account.setAccountBalance(accountRequest.getAccBalance());

        Branch branch = new Branch();
        branch.setBranchId(accountRequest.getBranchId());
        account.setBranchId(branch);

        Customer customer= new Customer();
        customer.setCustId(accountRequest.getCustomerId());
        account.setCustomers(customer);

        Account accountResp= accountRepo.save(account);
        if(accountResp==null){
            log.info("Account is not saved");
            throw new AccountDetailsNotFound("Account not saved !");
        }
        AccountTO accountTO= new AccountTO();
        accountTO.setAccNo(accountResp.getAccountNo());
        accountTO.setAccType(accountResp.getAccountType());
        accountTO.setAccBalance(accountResp.getAccountBalance());

        return accountTO;
    }

    @Override
    public AccountTO updateAccount(AccountRequest accountRequest) throws AccountDetailsNotFound {

        Account account= new Account();
        account.setAccountNo(accountRequest.getAccNo());
        account.setAccountType(accountRequest.getAccType());
        account.setAccountBalance(accountRequest.getAccBalance());

        Branch branch = new Branch();
        branch.setBranchId(accountRequest.getBranchId());
        account.setBranchId(branch);

        Customer customer= new Customer();
        customer.setCustId(accountRequest.getCustomerId());
        account.setCustomers(customer);

        Account accountResp= accountRepo.save(account);
        if(accountResp==null){
            log.info("Account is not updated");
            throw new AccountDetailsNotFound("Account not updated !");
        }
        AccountTO accountTO= new AccountTO();
        accountTO.setAccNo(accountResp.getAccountNo());
        accountTO.setAccType(accountResp.getAccountType());
        accountTO.setAccBalance(accountResp.getAccountBalance());

        return accountTO;
    }

    @Override
    public String deleteAccountByNo(int accountNo) throws AccountDetailsNotFound {
        Optional<Account> accOp= accountRepo.findById(accountNo);
        if(accOp==null){
            log.info("Account does not exist");
            throw new AccountDetailsNotFound("Account does not exist");
        }
       accountRepo.deleteById(accountNo);
        log.info("Account is deleted by code:{}", accountNo);
        return "Account is deleted by account No: "+ accountNo;
    }

    @Transactional
    @Override
    public String deleteAccountByType(String accountType) throws AccountDetailsNotFound {
        Optional<Account> accOp= accountRepo.findByAccountType(accountType);
        if(accOp==null){
            log.info("Account does not exist");
            throw new AccountDetailsNotFound("Account does not exist");
        }
        accountRepo.deleteByAccountType(accountType);
        log.info("Account is deleted by type:{}", accountType);
        return "Account is deleted by account Type: "+ accountType;
    }
}