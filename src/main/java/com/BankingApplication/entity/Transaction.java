package com.BankingApplication.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
@Table(name = "bank_transaction")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Transac_id")
  private int  transacId;

  @Column(name = "Transac_amt")
  private int transacAmount;

  @Column(name = "Transac_date")
  private String transacDate;

  @JoinColumn(name = "Cust_id")
  private int custId;

  @Column(name = "Transac_status")
  private String transacStatus;

}
