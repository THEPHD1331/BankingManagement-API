package com.BankingApplication.service;

import com.BankingApplication.exception.AccountDetailsNotFound;
import com.BankingApplication.model.AccountRequest;
import com.BankingApplication.model.AccountTO;
import java.util.List;

public interface AccountService {

    List<AccountTO> findAllAccounts() throws AccountDetailsNotFound;
    AccountTO getAccByID(int AccNo) throws AccountDetailsNotFound;
    AccountTO getAccByType(String accountType) throws AccountDetailsNotFound;
    AccountTO saveAccount(AccountRequest accountRequest) throws AccountDetailsNotFound;
    AccountTO updateAccount(AccountRequest accountRequest) throws AccountDetailsNotFound;
    String deleteAccountByNo(int accountNo) throws AccountDetailsNotFound;
    String deleteAccountByType(String accountType) throws AccountDetailsNotFound;
}
