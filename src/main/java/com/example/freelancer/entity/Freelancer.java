package com.example.freelancer.entity;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.FreelancerDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Freelancer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private Gender gender;
    private String phone;
    private String experience;
    @Column(columnDefinition="TEXT")
    private String description;
    private String title;
    private double averageIncome;
    private String language;
    private double rate;
    private String thumbnail;
    private Status status;
    private Date created_at;
    private Date updated_at;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(unique = true, insertable = false, updatable = false)
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

    public FreelancerDTO toFreelancerDTO() {
        FreelancerDTO freelancerDTO = new FreelancerDTO();
        freelancerDTO.setId(this.id);
        freelancerDTO.setName(this.name);
        freelancerDTO.setAddress(this.address);
        freelancerDTO.setGender(this.gender);
        freelancerDTO.setPhone(this.phone);
        freelancerDTO.setExperience(this.experience);
        freelancerDTO.setDescription(this.description);
        freelancerDTO.setTitle(this.title);
        freelancerDTO.setAverageIncome(this.averageIncome);
        freelancerDTO.setLanguage(this.language);
        freelancerDTO.setRate(this.rate);
        freelancerDTO.setStatus(this.status);
        freelancerDTO.setThumbnail(this.thumbnail);
        freelancerDTO.setAccount(new AccountDTO(this.account));
        return freelancerDTO;
    }

    public FreelancerDTO toFreelancerDTO2() {
        FreelancerDTO freelancerDTO = new FreelancerDTO();
        freelancerDTO.setId(this.id);
        freelancerDTO.setName(this.name);
        freelancerDTO.setAddress(this.address);
        freelancerDTO.setGender(this.gender);
        freelancerDTO.setPhone(this.phone);
        freelancerDTO.setExperience(this.experience);
        freelancerDTO.setDescription(this.description);
        freelancerDTO.setTitle(this.title);
        freelancerDTO.setAverageIncome(this.averageIncome);
        freelancerDTO.setLanguage(this.language);
        freelancerDTO.setRate(this.rate);
        freelancerDTO.setStatus(this.status);
        freelancerDTO.setThumbnail(this.thumbnail);
        return freelancerDTO;
    }

    public Freelancer(String name, String address, Gender gender, String phone, String experience, String description, String title, double averageIncome, String language, double rate, String thumbnail, Status status, Account account) {
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.phone = phone;
        this.experience = experience;
        this.description = description;
        this.title = title;
        this.averageIncome = averageIncome;
        this.language = language;
        this.rate = rate;
        this.thumbnail = thumbnail;
        this.status = status;
        this.account = account;
    }

//    @Override
//    public String toString() {
//        return "Freelancer{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", address='" + address + '\'' +
//                ", gender=" + gender +
//                ", phone='" + phone + '\'' +
//                ", experience='" + experience + '\'' +
//                ", description='" + description + '\'' +
//                ", title='" + title + '\'' +
//                ", averageIncome=" + averageIncome +
//                ", language='" + language + '\'' +
//                ", rate=" + rate +
//                ", thumbnail='" + thumbnail + '\'' +
//                ", status=" + status +
//                ", account=" + account +
//                ", accountId=" + accountId +
//                ", job=" + job +
//                '}';
//    }
}
