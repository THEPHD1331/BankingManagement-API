package com.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bank_account")
public class Account {

    @Id
    @Column(name = "Acc_no")
    private int accountNo;

    @Column(name = "Acc_type")
    private String accountType;

    @Column(name = "Acc_balance")
    private int accountBalance;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branchId;

    @ManyToOne
    @JoinColumn(name = "Cust_id") //(Bi-d) Many accounts to one customer
    private Customer customers;
}
