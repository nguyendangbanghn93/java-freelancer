package com.example.freelancer.service;

import com.example.freelancer.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialService {
    @Autowired
    private CredentialRepository credentialRepository;
    public long findUser(String token){
      return 1;
    }
}
