package com.example.asmAuth.entity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String fullName;
    private String passwordHash;
    private int status;
    private Date createdAt;
    private  Date updatedAt;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Credential> credentials;
    private int role;

    public String getRoleName() {
        return role == 1 ? "ADMIN" : "USER";
    }
}


