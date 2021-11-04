package com.example.freelancer.repository;

import com.example.freelancer.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByUsername(String username);
    Optional<Account> findAccountByEmail(String username);

    @Query("SELECT a FROM account a WHERE a.role = :role AND a.status != 2 ORDER BY createdAt DESC")
    Page<Account> findAllByRole(Account.Role role, Pageable pageable);
}
