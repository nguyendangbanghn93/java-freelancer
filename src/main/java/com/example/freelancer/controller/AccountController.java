package com.example.freelancer.controller;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.CredentialDTO;
import com.example.freelancer.dto.FreelancerDTO;
import com.example.freelancer.dto.LoginDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @RequestMapping(method = RequestMethod.GET, value = "/api/account/{id}")
    public ResponseAPI<AccountDTO> getDetailAccount(
            @PathVariable(value = "id") @Nullable Integer id
    ) {
        Account account = accountService.findById(id);
        if (account != null) {
            return new ResponseAPI<AccountDTO>(new AccountDTO(account), APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }
        return new ResponseAPI<AccountDTO>(APIMessage.MES_ERROR, APIStatusCode.ERROR);
    }
}