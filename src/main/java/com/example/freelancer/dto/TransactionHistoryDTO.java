package com.example.freelancer.dto;

import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.TransactionHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryDTO {
    private int id;
    private double amount;
    private AccountDTO account;
    private int accountId;
    private Date createdAt;
    private Date updatedAt;
    private int type;

    public TransactionHistoryDTO(TransactionHistory transactionHistory) {
        this.id = transactionHistory.getId();
        this.amount = transactionHistory.getAmount();
        this.account = new AccountDTO(transactionHistory.getAccount());
        this.accountId = transactionHistory.getAccountId();
        this.createdAt = transactionHistory.getCreatedAt();
        this.updatedAt = transactionHistory.getUpdatedAt();
        this.type = transactionHistory.getType();
    }
}
