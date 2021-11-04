package com.example.freelancer.repository;

import com.example.freelancer.entity.Freelancer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FreelancerRepository extends JpaRepository<Freelancer,Integer> {
    @Query("SELECT f FROM freelancer f WHERE f.accountId = :accountId")
    Optional<Freelancer> findByAccountId(@Param(value="accountId") Integer accountId);

    @Query("SELECT f FROM freelancer f WHERE f.status != 2 ORDER BY created_at DESC")
    Page<Freelancer> findAll(Pageable pageable);
}
