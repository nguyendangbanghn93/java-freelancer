package com.example.freelancer.repository;


import com.example.freelancer.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, String> {
    public Credential findCredentialByAccessToken(String token);
}
