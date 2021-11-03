package com.example.freelancer.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobDTO {
    private int id;
    private double salary;
    private String subject;
    private String description;
    private int type;
    private int status;
    private String result;
    private Date response_date;
    private String invited_user_id;
    private int accountId;
    private int freelancerId;
    private Date created_at;
    private Date updated_at;
    private double rate;
    private String comment;
    private AccountDTO account;
    private FreelancerDTO freelancer;

    public JobDTO(int id, double salary, String subject, String description, int type, int status, String result, Date response_date, String invited_user_id, int accountId, int freelancerId, Date created_at, Date updated_at, double rate, String comment) {
        this.id = id;
        this.salary = salary;
        this.subject = subject;
        this.description = description;
        this.type = type;
        this.status = status;
        this.result = result;
        this.response_date = response_date;
        this.invited_user_id = invited_user_id;
        this.accountId = accountId;
        this.freelancerId = freelancerId;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.rate = rate;
        this.comment = comment;
    }
}
