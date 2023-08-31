package com.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
@Table(name = "bank_customer")
public class Customer {

    @Id
    @Column(name = "Cust_id")
    private int custId;

    @Column(name = "Cust_name")
    private String custName;

    @Column(name = "Cust_phone")
    private long custPhone;

    @Column(name = "Cust_address")
    private String custAddress;

    @OneToMany(mappedBy = "customers", cascade = CascadeType.ALL) // One customer to many accounts
    private Set<Account> accountSet;

    @OneToMany(mappedBy = "customerz", cascade = CascadeType.ALL) // One Customer to many loans
    private Set<Loan> loanSet;
}
