package com.example.freelancer.service;

import com.example.freelancer.dto.TransactionHistoryDTO;
import com.example.freelancer.entity.TransactionHistory;
import com.example.freelancer.repository.AccountRepository;
import com.example.freelancer.repository.SystemConfigRepository;
import com.example.freelancer.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class TransactionService {
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    SystemConfigRepository systemConfigRepository;

    @Autowired
    AccountRepository accountRepository;

    public void makeTransaction(){

    }

    public TransactionHistory createTransactionHistory(TransactionHistoryDTO transactionHistoryDTO) {
        try {
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setAmount(transactionHistoryDTO.getAmount());
            transactionHistory.setAccountId(transactionHistoryDTO.getAccountId());
            transactionHistory.setAccount(accountRepository.findById(transactionHistoryDTO.getAccountId()).get());
            transactionHistory.setCreatedAt(new Date());
            transactionHistory.setUpdatedAt(new Date());
            transactionHistory.setType(transactionHistoryDTO.getType());
            transactionHistoryRepository.save(transactionHistory);

            return transactionHistory;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
