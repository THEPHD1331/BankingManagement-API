package com.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "bank")
public class Bank implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Bank_code")
    private int bankCode;

    @Column(name = "Bank_name")
    private String bankName;

    @Column(name = "Bank_address")
    private String bankAddress;

    @OneToMany(mappedBy = "bankCode", cascade = CascadeType.ALL) // One bank to many branches
    private Set<Branch> branchSet;

}
