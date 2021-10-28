package com.example.freelancer.dto;

import com.example.freelancer.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AccountDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private Double amount;
    private Date createdAt;
    private  Date updatedAt;
    private Account.Role role;
    private Account.Status status;
    private FreelancerDTO freelancerDTO;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
        this.role = account.getRole();
        this.amount = account.getAmount();
        this.status = account.getStatus();
        this.email = account.getEmail();
    }
}
