package com.example.freelancer.seeding;

import com.example.freelancer.entity.Account;
import com.example.freelancer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Seeding implements CommandLineRunner {


    @Autowired
    AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (accountService.count() == 0) {
            accountService.createAcount("hongluyen",  "Hồng luyến ","123456", Account.Role.USER, Account.Status.ACTIVATE);
            accountService.createAcount("hongduc",  "Hồng Đức","123456", Account.Role.USER, Account.Status.ACTIVATE);
            accountService.createAcount("hongtruong",  "Hồng Trường","123456", Account.Role.USER, Account.Status.ACTIVATE);
            accountService.createAcount("honghung",  "Hồng Hưng","123456", Account.Role.USER, Account.Status.ACTIVATE);
        }
    }
}
