package com.example.freelancer.entity;

import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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
    @NotEmpty(message = "username notnull")
    @Column(unique = true)
    private String username;
    @NotEmpty(message = "Email notnull")
    @Column(unique = true, nullable = false)
    private String email;
    //    @NotNull
    private String passwordHash;
    //    @NotNull
    private Double amount;
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

    public Account(@NotNull String username, @NotNull String email, @NotNull String passwordHash, Status status, Role role, Double amount, Date createdAt, Date updatedAt) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = status;
        this.role = role;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

//    @Override
//    public String toString() {
//        return "Account{" +
//                "id=" + id +
//                ", username='" + username + '\'' +
//                ", email='" + email + '\'' +
//                ", passwordHash='" + passwordHash + '\'' +
//                ", amount=" + amount +
//                ", status=" + status +
//                ", role=" + role +
//                ", createdAt=" + createdAt +
//                ", updatedAt=" + updatedAt +
//                ", credentials=" + credentials +
//                ", freelancer=" + freelancer +
//                ", jobSet=" + jobSet +
//                '}';
//    }
}


