package com.example.freelancer.controller;

import com.example.freelancer.dto.TransactionHistoryDTO;
import com.example.freelancer.entity.TransactionHistory;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.resdto.TransactionHistoryRes;
import com.example.freelancer.service.TransactionService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/transaction")
    public ResponseAPI<TransactionHistoryRes> listTransaction() {
        TransactionHistoryRes transactionHistories = transactionService.allTranscationHistory();
        if (transactionHistories != null) {
            return new ResponseAPI(transactionHistories, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }
        return new ResponseAPI(APIMessage.MES_ERROR, APIStatusCode.ERROR);
    }
}
