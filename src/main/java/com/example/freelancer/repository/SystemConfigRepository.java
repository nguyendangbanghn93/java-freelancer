package com.example.freelancer.repository;

import com.example.freelancer.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemConfigRepository extends JpaRepository<SystemConfig,Integer> {
    Optional<SystemConfig> findTopByOrderByIdDesc();
    Optional<SystemConfig> findTopByOrderById();
}
