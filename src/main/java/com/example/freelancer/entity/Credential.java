package com.example.freelancer.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Credential {
    @Id
    @Column(columnDefinition = "varchar(100)")
    private String accessToken;
    private String refreshToken;
    private Date createdAt;
    private Date expiredAt;
    private int status;
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private Account account;
    @Column(insertable = false, updatable = false)
    private int accountId;

    public boolean isExpired() {
        return new Date().getTime() > expiredAt.getTime();
    }
}
