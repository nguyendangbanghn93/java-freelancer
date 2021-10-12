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
    private String email;
    private String passwordHash;
    private Status status;
    private Role role;
    private Date createdAt;
    private  Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Credential> credentials;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Freelancer freelancer;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Job> jobSet;

    public enum Status {
        ACTIVATE,
        DEACTIVATE,
        DELETE,
    }

    public enum Role {
        ADMIN,
        USER,
        FREELANCER
    }
}


