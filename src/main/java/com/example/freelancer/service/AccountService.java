package com.example.freelancer.service;

import com.example.freelancer.dto.CredentialDTO;
import com.example.freelancer.dto.LoginDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Credential;
import com.example.freelancer.repository.AccountRepository;
import com.example.freelancer.repository.CredentialRepository;
import com.example.freelancer.util.TimeUtil;
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
        System.out.println("token vào accountService: "+accessToken);
        Optional<Credential> credentialOptional = credentialRepository.findById(accessToken);
        System.out.println("token vào accountService: "+accessToken); //2118b1e0-2f41-4e0c-a952-0465153fea72 (Có trong cơ sở dữ liệu)
        if (credentialOptional.isPresent()) {
            Credential credential = credentialOptional.get();
            //có chạy vào đây
            System.out.println("Có tài khoản không: " +credential.getAccountId()); //acount id = 6
            if (credential.isExpired()) {
                return null;
            }
            return accountRepository.findById(credential.getAccountId()).orElse(null);
        }
        return null;
    }
    public Account createAcount(String username, String fullname, String password, Account.Role role, Account.Status status) {
        Account account = new Account();
        account.setUsername(username);
        account.setPasswordHash(passwordEncoder.encode(password));
        account.setRole(role);
        account.setStatus(status);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        return accountRepository.save(account);
    }
    public Account register(LoginDTO loginDTO) {
        Account account = new Account();
        account.setRole(Account.Role.ADMIN);
        account.setUsername(loginDTO.getUsername());
        account.setPasswordHash(passwordEncoder.encode(loginDTO.getPassword()));
        account.setStatus(Account.Status.ACTIVATE);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
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
    public long count(){
        return accountRepository.count();
    }
}
