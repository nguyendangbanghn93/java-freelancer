package com.example.freelancer.entity;

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
    @JoinColumn(name = "accountId")
    private Account account;
    @Column(insertable = false, updatable = false)
    private int accountId;
    //1 = Withdraw , 2 = Charge
    private int type;
    private Date createdAt;
    private Date updatedAt;

    public TransactionHistoryDTO toTransactionHistoryDTO() {
        return new TransactionHistoryDTO(id, amount, account, accountId, createdAt, updatedAt, type);
    }
}
