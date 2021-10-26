package com.example.freelancer.repository;

import com.example.freelancer.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory,Integer> {
}
