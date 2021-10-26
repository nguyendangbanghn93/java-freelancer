package com.example.freelancer.repository;

import com.example.freelancer.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer,Integer> {
    @Query("SELECT f FROM Freelancer f WHERE f.accountId = :accountId")
    Optional<Freelancer> findByAccountId(@Param(value="accountId") Integer accountId);
}
