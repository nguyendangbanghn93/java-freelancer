package com.example.freelancer.service;

import com.example.freelancer.entity.Job;
import com.example.freelancer.repository.FreelancerRepository;
import com.example.freelancer.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Configurable
public class JobService {
    @Autowired
    JobRepository jobRepository;
    public Job getDetailJob(Integer id){
        return jobRepository.findById(id).get();
    }
}
