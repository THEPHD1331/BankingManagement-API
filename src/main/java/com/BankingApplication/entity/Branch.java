package com.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "bank_branch")
public class Branch  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private int branchId;

    @Column(name = "br_name")
    private String branchName;

    @Column(name = "br_address")
    private String branchAddress;

    @ManyToOne
    @JoinColumn(name = "Bank_code") // (Bi-d) many branches to one bank
    private Bank bankCode;
}
