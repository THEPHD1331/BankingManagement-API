package com.BankingApplication.model;

import lombok.Data;

@Data
public class TransactionRequest {

    private int transacId;
    private int transacAmt;
    private String transacDate;
    private int custId;
    private String transacStatus;
}
