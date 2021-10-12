package com.example.freelancer.entity;
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
        switch (role){
            case 1:
                return  "ADMIN";
            case 2:
                return  "USER";
            case 3:
                return "FREELANCER";
            default:
                return "GUEST";
        }
    }
}


