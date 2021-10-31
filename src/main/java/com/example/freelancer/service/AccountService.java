package com.example.freelancer.service;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.CredentialDTO;
import com.example.freelancer.dto.LoginDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Credential;
import com.example.freelancer.repository.AccountRepository;
import com.example.freelancer.repository.CredentialRepository;
import com.example.freelancer.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
        System.out.println("token vào accountService: " + accessToken);
        Optional<Credential> credentialOptional = credentialRepository.findById(accessToken);
        System.out.println("token vào accountService: " + accessToken); //2118b1e0-2f41-4e0c-a952-0465153fea72 (Có trong cơ sở dữ liệu)
        if (credentialOptional.isPresent()) {
            Credential credential = credentialOptional.get();
            //có chạy vào đây
            System.out.println("Có tài khoản không: " + credential.getAccountId()); //acount id = 6
            if (credential.isExpired()) {
                return null;
            }
            return accountRepository.findById(credential.getAccountId()).orElse(null);
        }
        return null;
    }

    public Account createAccount(AccountDTO accountDTO) {
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPasswordHash(passwordEncoder.encode(accountDTO.getPassword()));
        account.setRole(accountDTO.getRole());
        account.setEmail(accountDTO.getEmail());
        account.setAmount(accountDTO.getAmount());
        account.setStatus(Account.Status.ACTIVATE);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        return accountRepository.save(account);
    }

    public Account createAccount2(Account account) {
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        return accountRepository.save(account);
    }

    public Account register(LoginDTO loginDTO) {
        Account account = new Account();
        account.setRole(Account.Role.USER);
        account.setUsername(loginDTO.getUsername());
        account.setAmount((double) 0);
        account.setEmail(loginDTO.getEmail());
        account.setPasswordHash(passwordEncoder.encode(loginDTO.getPassword()));
        account.setStatus(Account.Status.ACTIVATE);
        account.setCreatedAt(new Date());
        account.setUpdatedAt(new Date());
        return accountRepository.save(account);
    }

    public CredentialDTO login(LoginDTO loginDTO) {
        Optional<Account> accountOptional = accountRepository.findAccountByEmail(loginDTO.getEmail());
        if (!accountOptional.isPresent()) {
            accountOptional = accountRepository.findAccountByUsername(loginDTO.getUsername());
            if (!accountOptional.isPresent())
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
        credential.setAccountId(account.getId());
        credential.setCreatedAt(new Date());
        credential.setExpiredAt(TimeUtil.addDaysToCurrentTime(5));//Thời hạn 5 ngày
        Credential saved = credentialRepository.save(credential);
        return new CredentialDTO(saved);

    }

    public Account findById(Integer id) {
        Optional<Account> opt = accountRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    public boolean updateAccount(AccountDTO accountDTO) {
        Optional<Account> opt = accountRepository.findById(accountDTO.getId());
        if (opt.isPresent()) {
            Account account = opt.get();
            account.setUsername(accountDTO.getUsername());
            account.setAmount(accountDTO.getAmount());
            if (accountDTO.getPassword() != null) {
                account.setPasswordHash(passwordEncoder.encode(accountDTO.getPassword()));
            }
            account.setUpdatedAt(new Date());
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public boolean deleteAccount(int accountId) {
        Optional<Account> opt = accountRepository.findById(accountId);
        if (opt.isPresent()) {
            Account account = opt.get();
            account.setStatus(Account.Status.DELETE);
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    public long count() {
        return accountRepository.count();
    }

    public Page<Account> getListAccount(
            @Nullable Integer currentPage,
            @Nullable Integer pageSize,
            @Nullable Integer typeUser
    ) {
        if (currentPage == null) {
            currentPage = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Account.Role role;
        switch (typeUser) {
            case 0:
                role = Account.Role.ADMIN;
                break;
            case 1:
                role = Account.Role.USER;
                break;
            case 2:
                role = Account.Role.FREELANCER;
                break;
            default:
                role = Account.Role.USER;
                break;
        }

        Page<Account> result = accountRepository.findAllByRole(role, pageable);
        return result;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }
}
