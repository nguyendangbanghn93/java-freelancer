package com.example.freelancer.repository;

import com.example.freelancer.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Integer> {
    @Query("SELECT SUM(m.amount) FROM transaction_history m")
    double getSumTransaction();

    @Query("SELECT m.createdAt,SUM(m.amount) FROM transaction_history m WHERE m.createdAt BETWEEN :startDate and :endDate GROUP BY m.createdAt")
    List<Object[]> getTransactionHistoryByCreatedAtBetween(Date startDate, Date endDate);
}
