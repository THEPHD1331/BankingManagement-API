package com.BankingApplication.repository;

import com.BankingApplication.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRepository extends JpaRepository<Bank, Integer> {

    Optional<Bank> findByBankName(String bankName) ;
    void deleteByBankName(String bankName);
 }
