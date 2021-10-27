package com.example.freelancer.service;

import com.example.freelancer.dto.TransactionHistoryDTO;
import com.example.freelancer.entity.TransactionHistory;
import com.example.freelancer.repository.AccountRepository;
import com.example.freelancer.repository.SystemConfigRepository;
import com.example.freelancer.repository.TransactionHistoryRepository;
import com.example.freelancer.resdto.TransactionHistoryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TransactionService {
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    SystemConfigRepository systemConfigRepository;

    @Autowired
    AccountRepository accountRepository;

    public TransactionHistoryRes allTranscationHistory(){
        try {
            TransactionHistoryRes transactionHistoryRes = new TransactionHistoryRes();
            transactionHistoryRes.setList(transactionHistoryRepository.findAll().stream().map(x -> x.toTransactionHistoryDTO()).collect(Collectors.toList()));
            transactionHistoryRes.setTotalSum(transactionHistoryRepository.getSumTransaction());

            return transactionHistoryRes;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public TransactionHistory createTransactionHistory(TransactionHistory transactionHistoryItem) {
        try {
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setAmount(transactionHistoryItem.getAmount());
            transactionHistory.setAccountId(transactionHistoryItem.getAccountId());
            transactionHistory.setAccount(accountRepository.findById(transactionHistoryItem.getAccountId()).get());
            transactionHistory.setCreatedAt(new Date());
            transactionHistory.setUpdatedAt(new Date());
            transactionHistory.setType(transactionHistoryItem.getType());
            transactionHistoryRepository.save(transactionHistory);

            return transactionHistory;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
