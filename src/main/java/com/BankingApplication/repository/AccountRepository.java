package com.BankingApplication.repository;

import com.BankingApplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByAccountType(String accountType);
    void deleteByAccountType(String accountType);
}
