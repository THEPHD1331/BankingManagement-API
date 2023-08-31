package com.BankingApplication.repository;

import com.BankingApplication.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
    Optional<Branch> findByBranchName(String branchName);
    void deleteByBranchName(String branchName);

}
