package com.BankingApplication.service;

import com.BankingApplication.exception.BankDetailsNotFound;
import com.BankingApplication.model.BankRequest;
import com.BankingApplication.model.BankTO;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BankService {

 List<BankTO> getAllBanks() throws BankDetailsNotFound;
BankTO getBankByCode(int bankCode) throws BankDetailsNotFound;
BankTO getBankByName(String bankName) throws BankDetailsNotFound;
BankTO saveBank(BankRequest bankRequest) throws BankDetailsNotFound;
BankTO updateBank(BankRequest bankRequest) throws BankDetailsNotFound;
String deleteBankByCode(int bankCode) throws BankDetailsNotFound;
String deleteBankByName(String bankName) throws BankDetailsNotFound;
CompletableFuture<BankTO> findBankByCode(int bankCode) throws InterruptedException, BankDetailsNotFound;
CompletableFuture<BankTO> findBankByName(String bankName) throws BankDetailsNotFound, InterruptedException;

}
