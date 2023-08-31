package com.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "bank_loan")
public class Loan {

    @Id
    @Column(name = "Loan_id")
    private int loanId;

    @Column(name = "Loan_type")
    private String loanType;

    @Column(name = "Loan_amt")
    private int loanAmount;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branchId;

    @ManyToOne
    @JoinColumn(name = "Cust_id") //(Bi-d) many loans to one customer
    private Customer customerz;

}
