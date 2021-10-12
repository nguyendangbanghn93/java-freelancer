package com.example.freelancer.controller;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.CredentialDTO;
import com.example.freelancer.dto.LoginDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@CrossOrigin
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CredentialDTO login(@RequestBody LoginDTO loginDTO) {
        CredentialDTO credential = accountService.login(loginDTO);
        if (credential == null) {
            return null;
        }
        return credential;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public AccountDTO register(@RequestBody LoginDTO loginDTO) {
        Account register = accountService.register(loginDTO);
        if (register == null) {
            return null;
        }
        return new AccountDTO(register);
    }
}