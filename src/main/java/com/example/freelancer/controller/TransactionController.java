package com.example.freelancer.controller;

import com.example.freelancer.dto.ShortTransactionDTO;
import com.example.freelancer.dto.TransactionHistoryDTO;
import com.example.freelancer.entity.TransactionHistory;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.resdto.TransactionHistoryRes;
import com.example.freelancer.service.TransactionService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET, value = "/transaction")
    public ResponseAPI<Object[]> listTransactionFilter(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable Date startDate,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable Date endDate) {
        List<Object[]> transactionHistories = transactionService.allTranscationHistory(startDate, endDate);
        if (transactionHistories != null) {
            return new ResponseAPI(transactionHistories, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }
        return new ResponseAPI(APIMessage.MES_ERROR, APIStatusCode.ERROR);
    }
}
