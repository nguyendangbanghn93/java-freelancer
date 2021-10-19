package com.example.freelancer.repository;

import com.example.freelancer.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job,Integer> {
}
