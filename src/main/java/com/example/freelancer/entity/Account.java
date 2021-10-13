package com.example.freelancer.entity;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String passwordHash;
    private Status status;
    private Role role;
    private Date createdAt;
    private Date updatedAt;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
    private Set<Credential> credentials;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
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


