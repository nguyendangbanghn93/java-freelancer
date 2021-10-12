package com.example.freelancer.controller;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public AccountDTO getInformation(@RequestHeader("Authorization") String token) {
        Account account = accountService.findByToken(token.replace("Bearer", "").trim());
        System.out.println("tài khoản tương ứng: " + account.toString()); // Đã lấy được tên là hông luyến
        return new AccountDTO(account);
    }
}
