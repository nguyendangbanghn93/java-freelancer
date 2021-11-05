package com.example.freelancer.entity;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.FreelancerDTO;
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
@Table(name = "job")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double salary;
    private String subject;
    @Column(columnDefinition="TEXT")
    private String description;
    private int type;
    private int status;
    private String result;
    private Date response_date;
    private String invited_user_id;
    private Date created_at;
    private Date updated_at;
    private double rate;
    private String comment;

    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Chat chat;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rate> rateSet;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", insertable = false, updatable = false)
    private Account account;
    @Column
    private int accountId;


    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "freelancerId", insertable = false, updatable = false)
    private Freelancer freelancer;
    @Column
    private int freelancerId;
    public JobDTO toJobDTO(){
        return new JobDTO(id,salary,subject,description,type,status,result,response_date,invited_user_id,accountId,freelancerId, created_at, updated_at, rate, comment);
    }

    public JobDTO toJobDTO2(){
        return new JobDTO(
            id,
            salary,
            subject,
            description,
            type,
            status,
            result,
            response_date,
            invited_user_id,
            accountId,
            freelancerId,
            created_at,
            updated_at,
            rate,
            comment,
            new AccountDTO(account),
            freelancer.toFreelancerDTO()
        );
    }

    public Job(double salary, String subject, String description, int type, int status, String result, Date response_date, double rate, String comment, int accountId, int freelancerId, Date created_at, Date updated_at) {
        this.salary = salary;
        this.subject = subject;
        this.description = description;
        this.type = type;
        this.status = status;
        this.result = result;
        this.response_date = response_date;
        this.rate = rate;
        this.comment = comment;
        this.accountId = accountId;
        this.freelancerId = freelancerId;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}
