package com.example.asmAuth.service;

import com.example.asmAuth.entity.Credential;
import com.example.asmAuth.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CredentialService {
    @Autowired
    private CredentialRepository credentialRepository;
    public long findUser(String token){
      return 1;
    }
}
