package com.example.freelancer.dto;
import com.example.freelancer.entity.Credential;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@ToString
public class CredentialDTO {
    private String accessToken;
    private String refreshToken;
    private Date createdAt;
    private boolean isExpired;
    private int status;

    public CredentialDTO(Credential credential) {
        this.accessToken = credential.getAccessToken();
        this.refreshToken = credential.getRefreshToken();
        this.createdAt = credential.getCreatedAt();
        this.isExpired = credential.isExpired();
        this.status = credential.getStatus();
    }
}
