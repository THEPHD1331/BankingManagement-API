package com.BankingApplication.repository;

import com.BankingApplication.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByCustName(String custName);
    void deleteByCustName(String custName);
}
