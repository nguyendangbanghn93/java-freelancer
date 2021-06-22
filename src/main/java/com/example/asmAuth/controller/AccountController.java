package com.example.asmAuth.controller;

import com.example.asmAuth.dto.CredentialDTO;
import com.example.asmAuth.dto.LoginDTO;
import com.example.asmAuth.entity.Account;
import com.example.asmAuth.service.AccountService;
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
    public String register(@RequestBody LoginDTO loginDTO) {
        Account register = accountService.register(loginDTO);
        if (register == null) {
            return "failed";
        }
        return "ok";
    }
}
