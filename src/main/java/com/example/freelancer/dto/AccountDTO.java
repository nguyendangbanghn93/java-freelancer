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
    private String fullName;
    private Date createdAt;
    private  Date updatedAt;
    private int role;
    private int status;
    public AccountDTO(Account account) {

        this.id = account.getId();
        this.username = account.getUsername();
        this.fullName = account.getFullName();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
        this.role = account.getRole();
        this.status = account.getStatus();
    }
}
