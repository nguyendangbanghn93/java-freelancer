package com.example.freelancer.repository;

import com.example.freelancer.entity.Freelancer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreelancerRepository extends JpaRepository<Freelancer,Integer> {
}
