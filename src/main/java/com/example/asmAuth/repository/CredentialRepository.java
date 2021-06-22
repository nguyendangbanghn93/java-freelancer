package com.example.asmAuth.repository;


import com.example.asmAuth.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, String> {
}
