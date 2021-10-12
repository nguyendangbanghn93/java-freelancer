package com.example.freelancer.controller;

import com.example.freelancer.dto.CredentialDTO;
import com.example.freelancer.dto.LoginDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/test")
@CrossOrigin
public class TestController {
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String testAdmin() {
       return "Tôi là admin";
    }
}
