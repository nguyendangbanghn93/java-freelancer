package com.example.freelancer.controller;

import com.example.freelancer.dto.FreelancerDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.service.FreelancerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/freelancers")
@CrossOrigin
public class FreelancerController {
    @Autowired
    AccountService accountService;
    @Autowired
    FreelancerService freelancerService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public FreelancerDTO registerFreelancer(@RequestHeader("Authorization") String token, @RequestBody FreelancerDTO freelancerDTO) {
        Account account = accountService.findByToken(token.replace("Bearer", "").trim());
        return new  FreelancerDTO(freelancerService.register(freelancerDTO,account));
    }


}
