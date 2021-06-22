package com.example.asmAuth.dto;

import com.example.asmAuth.entity.Account;
import com.example.asmAuth.entity.Credential;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

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
