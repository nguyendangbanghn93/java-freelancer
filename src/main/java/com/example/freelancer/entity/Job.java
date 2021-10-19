package com.example.freelancer.entity;

import com.example.freelancer.dto.JobDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double salary;
    private String subject;
    private String description;
    private int type;
    private int status;
    private Date response_date;
    private String invited_user_id;

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Chat chat;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rate> rateSet;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;
    @Column(insertable = false, updatable = false)
    private int accountId;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancerId")
    private Freelancer freelancer;
    @Column(insertable = false, updatable = false)
    private int freelancerId;
    public JobDTO toJobDTO(){
        return new JobDTO(id,salary,subject,description,type,status,response_date,invited_user_id,accountId,freelancerId);
    }
}
