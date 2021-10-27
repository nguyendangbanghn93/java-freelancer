package com.example.freelancer.controller;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.FreelancerDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.service.FreelancerService;
import com.example.freelancer.service.JobService;
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

    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/information", method = RequestMethod.GET)
    public AccountDTO getInformation(@RequestHeader("Authorization") String token) {
        Account account = accountService.findByToken(token.replace("Bearer", "").trim());
        System.out.println("tài khoản tương ứng: " + account.toString()); // Đã lấy được tên là hông luyến

        AccountDTO accountDTO = new AccountDTO(account);

        Freelancer freelancer = freelancerService.findByAccountId(account.getId());
        if (freelancer != null) {
            FreelancerDTO freelancerDTO = freelancer.toFreelancerDTO2();
            freelancerDTO.setTotalJobDone(jobService.getTotalJobDone(freelancer.getId()));
            freelancerDTO.setTotalEarning(jobService.getTotalEarning(freelancer.getId()));
            accountDTO.setFreelancerDTO(freelancerDTO);
        }

        return accountDTO;
    }
}
