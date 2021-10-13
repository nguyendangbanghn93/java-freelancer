package com.example.freelancer.dto;

import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FreelancerDTO {
    private String name;
    private String address;
    private Freelancer.Gender gender;
    private String phone;
    private String experience;
    private String description;
    private String title;
    private double averageIncome;
    private String language;
    private double rate;
    private Freelancer.Status status;
    private AccountDTO account;
    public FreelancerDTO(Freelancer freelancer){
        this.name = freelancer.getName();
        this.address = freelancer.getAddress();
        this.gender = freelancer.getGender();
        this.phone = freelancer.getPhone();
        this.experience = freelancer.getExperience();
        this.description = freelancer.getDescription();
        this.title = freelancer.getTitle();
        this.averageIncome = freelancer.getAverageIncome();
        this.language = freelancer.getName();
        this.rate = freelancer.getRate();
        this.status = freelancer.getStatus();
        this.account = new AccountDTO(freelancer.getAccount());
    }
}
