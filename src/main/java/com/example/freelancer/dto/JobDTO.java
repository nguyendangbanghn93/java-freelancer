package com.example.freelancer.dto;

import com.example.freelancer.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobDTO {
    private int id;
    private double salary;
    private String subject;
    private String description;
    private int type;
    private int status;
    private Date response_date;
    private String invited_user_id;
    private int accountId;
    private int freelancerId;

    public JobDTO(int id, double salary, String subject, String description, int type, int status, Date response_date, String invited_user_id, int accountId, int freelancerId) {
        this.id = id;
        this.salary = salary;
        this.subject = subject;
        this.description = description;
        this.type = type;
        this.status = status;
        this.response_date = response_date;
        this.invited_user_id = invited_user_id;
        this.accountId = accountId;
        this.freelancerId = freelancerId;
    }
}
