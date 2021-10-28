package com.example.freelancer.repository;

import com.example.freelancer.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByEmail(String username);

    Page<Account> findAllByRole(Account.Role role, Pageable pageable);
}
