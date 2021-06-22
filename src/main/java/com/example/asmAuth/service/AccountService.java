package com.example.asmAuth.service;

import com.example.asmAuth.dto.CredentialDTO;
import com.example.asmAuth.dto.LoginDTO;
import com.example.asmAuth.entity.Account;
import com.example.asmAuth.entity.Credential;
import com.example.asmAuth.repository.AccountRepository;
import com.example.asmAuth.repository.CredentialRepository;
import com.example.asmAuth.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CredentialRepository credentialRepository;

    public Account findByToken(String accessToken) {
        Optional<Credential> credentialOptional = credentialRepository.findById(accessToken);
        if (credentialOptional.isPresent()) {
            Credential credential = credentialOptional.get();
            if (credential.isExpired()) {
                return null;
            }
            return accountRepository.findById(credential.getAccountId()).orElse(null);
        }
        return null;
    }
    public Account createAcount(String username, String fullname, String password, int role, int status) {
        Account account = new Account();
        account.setUsername(username);
        account.setPasswordHash(passwordEncoder.encode(password));
        account.setFullName(fullname);
        account.setRole(role);
        account.setStatus(status);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        return accountRepository.save(account);
    }
    public Account register(LoginDTO loginDTO) {
        Account account = new Account();
        account.setRole(1);
        account.setUsername(loginDTO.getUsername());
        account.setPasswordHash(passwordEncoder.encode(loginDTO.getPassword()));
        account.setFullName("full name");
        account.setStatus(1);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        account.setFullName(loginDTO.getFullName());
        return accountRepository.save(account);
    }

    public CredentialDTO login(LoginDTO loginDTO) {
        Optional<Account> accountOptional = accountRepository.findAccountByUsername(loginDTO.getUsername());
        if (!accountOptional.isPresent()) {
            return null;
        }
        Account account = accountOptional.get();
        if (!passwordEncoder.matches(loginDTO.getPassword(), account.getPasswordHash())) {
            return null;
        }
        Credential credential = new Credential();
        credential.setAccessToken(UUID.randomUUID().toString());
        credential.setRefreshToken(UUID.randomUUID().toString());
        credential.setAccount(account);
        credential.setCreatedAt(new Date());
        credential.setExpiredAt(TimeUtil.addDaysToCurrentTime(5));//Thời hạn 5 ngày
        Credential saved = credentialRepository.save(credential);
        return new CredentialDTO(saved);

    }
}
