package com.example.freelancer.paypal;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.paypal.base.rest.APIContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

@Service
@Configurable
public class PaypalService {

	@Autowired
	private APIContext apiContext;
//	APIContext apiContext = new APIContext();
	public Payment createPayment(
			String content,
			Double paymentAmount,
			String cancelUrl,
			String successUrl) throws PayPalRESTException{

		Amount amount = new Amount();
		amount.setCurrency("USD");
		paymentAmount = new BigDecimal(paymentAmount).setScale(2, RoundingMode.HALF_UP).doubleValue();
		amount.setTotal(String.valueOf(paymentAmount));
		Transaction transaction = new Transaction();
		transaction.setDescription(content);
		transaction.setAmount(amount);
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(transaction);
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl(cancelUrl);
		redirectUrls.setReturnUrl(successUrl);
		payment.setRedirectUrls(redirectUrls);
		return payment.create(apiContext);
	}
	
	public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException{
		Payment payment = new Payment();
		payment.setId(paymentId);
		PaymentExecution paymentExecute = new PaymentExecution();
		paymentExecute.setPayerId(payerId);
		return payment.execute(apiContext, paymentExecute);
	}
}
