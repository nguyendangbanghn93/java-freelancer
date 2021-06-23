package com.example.asmAuth.controller;

import com.example.asmAuth.dto.AccountDTO;
import com.example.asmAuth.entity.Account;
import com.example.asmAuth.service.AccountService;
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
