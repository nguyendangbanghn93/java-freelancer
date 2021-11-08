package com.example.freelancer.repository;

import com.example.freelancer.entity.TransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Integer> {
    @Query("SELECT SUM(m.amount) FROM TransactionHistory m")
    double getSumTransaction();

    @Query("SELECT m.createdAt,SUM(m.amount) FROM TransactionHistory m WHERE m.createdAt BETWEEN :startDate and :endDate GROUP BY m.createdAt")
    List<Object[]> getTransactionHistoryByCreatedAtBetween(Date startDate, Date endDate);

    @Query("SELECT m FROM TransactionHistory m ORDER BY m.createdAt DESC")
    Page<TransactionHistory> findAll(Pageable pageable);
}
