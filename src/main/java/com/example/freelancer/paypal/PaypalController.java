package com.example.freelancer.paypal;

import javax.servlet.http.HttpServletRequest;

import com.example.freelancer.entity.Account;

import com.example.freelancer.repository.AccountRepository;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.service.JobService;
import com.example.freelancer.service.TransactionService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import com.paypal.api.payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.paypal.base.rest.PayPalRESTException;

@RestController
@CrossOrigin
@RequestMapping("job/payment")
public class PaypalController {
    private static final String AUTHORIZATION = "Authorization";
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JobService jobService;
    @Autowired
    TransactionService transactionService;

    @Autowired
    PaypalService service;
    private Payment payment = null;
    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @RequestMapping(value = "/create-payment", method = RequestMethod.POST)
    public Payment payment(@RequestParam Double amount, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath("job/payment/")
                .build()
                .toUriString();
        String content = "nap tien";
        try {
            payment = service.createPayment(content, amount, baseUrl + CANCEL_URL, baseUrl + SUCCESS_URL);
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        return payment;
    }

    @GetMapping(value = "/execute-payment")
    public ResponseAPI execute(@RequestParam("amount") Double amount, @RequestParam("accountId") Integer accountId) {
        Account account = accountService.findById(accountId);
        double newAmount = account.getAmount() + amount;
        account.setAmount(newAmount);
        accountRepository.save(account);

        return new ResponseAPI(APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
    }
}
