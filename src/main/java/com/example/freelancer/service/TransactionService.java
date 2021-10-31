package com.example.freelancer.service;

import com.example.freelancer.dto.ShortTransactionDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.TransactionHistory;
import com.example.freelancer.repository.AccountRepository;
import com.example.freelancer.repository.SystemConfigRepository;
import com.example.freelancer.repository.TransactionHistoryRepository;
import com.example.freelancer.resdto.TransactionHistoryRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class TransactionService {
    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    SystemConfigRepository systemConfigRepository;

    @Autowired
    AccountRepository accountRepository;

    public TransactionHistoryRes allTranscationHistory() {
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

    public List<Object[]> allTranscationHistory(Date startDate, Date endDate) {
        try {
            return transactionHistoryRepository.getTransactionHistoryByCreatedAtBetween(startDate, endDate);
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

    // admin
    public Page<TransactionHistory> getListTransactionPagination(
            @Nullable Integer currentPage,
            @Nullable Integer pageSize
    ) {
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<TransactionHistory> result = transactionHistoryRepository.findAll(pageable);
        return result;
    }
}
