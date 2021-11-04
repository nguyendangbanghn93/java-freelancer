package com.example.freelancer.entity;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.TransactionHistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double amount;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", insertable = false, updatable = false)
    private Account account;
    @Column
    private int accountId;
    //1 = Withdraw , 2 = Charge
    private int type;
    private Date createdAt;
    private Date updatedAt;

    public TransactionHistory(double amount, int accountId, int type, Date createdAt) {
        this.amount = amount;
        this.accountId = accountId;
        this.type = type;
        this.createdAt = createdAt;
    }

    public TransactionHistoryDTO toTransactionHistoryDTO() {
        return new TransactionHistoryDTO(id, amount, new AccountDTO(account), accountId, createdAt, updatedAt, type);
    }
}
