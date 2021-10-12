package com.example.freelancer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private Gender gender;
    private String phone;
    private String experience;
    private String description;
    private String title;
    private String school;
    private String language;
    private double rate;
    private Status status;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;
    @Column(insertable = false, updatable = false)
    private int accountId;

    @OneToMany(mappedBy = "freelancer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Job> job;

    public enum Gender {
        MALE,
        FEMALE,
        UNKNOWN
    }

    public enum Status {
        ACTIVATE,
        DEACTIVATE,
        DELETE,
    }
}