package com.example.freelancer.controller;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.service.FreelancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private FreelancerService freelancerService;

    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public AccountDTO getInformation(@RequestHeader("Authorization") String token) {
        Account account = accountService.findByToken(token.replace("Bearer", "").trim());
        System.out.println("tài khoản tương ứng: " + account.toString()); // Đã lấy được tên là hông luyến

        AccountDTO accountDTO = new AccountDTO(account);

        Freelancer freelancer = freelancerService.findByAccountId(account.getId());
        if (freelancer != null) {
            accountDTO.setFreelancerDTO(freelancer.toFreelancerDTO2());
        }

        return accountDTO;
    }
}
