package com.example.freelancer.service;

import com.example.freelancer.dto.JobDTO;
import com.example.freelancer.entity.Job;
import com.example.freelancer.repository.AccountRepository;
import com.example.freelancer.repository.FreelancerRepository;
import com.example.freelancer.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@Configurable
public class JobService {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    FreelancerRepository freelancerRepository;

    public Job getDetailJob(Integer id){
        return jobRepository.findById(id).get();
    }

    public Job createJob(JobDTO jobDTO) {
        try {
            Job job = new Job();
            job.setSalary(jobDTO.getSalary());
            job.setSubject(jobDTO.getSubject());
            job.setDescription(jobDTO.getDescription());
            job.setType(jobDTO.getType());
            job.setStatus(1);
            job.setResult(jobDTO.getResult());
            job.setResponse_date(new Date());
            job.setAccountId(jobDTO.getAccountId());
            job.setAccount(accountRepository.findById(jobDTO.getAccountId()).get());
            job.setFreelancerId(jobDTO.getFreelancerId());
            job.setFreelancer(freelancerRepository.findById(jobDTO.getFreelancerId()).get());
            jobRepository.save(job);
            return job;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Job updateJob(JobDTO jobDTO) {
        Optional opt = jobRepository.findById(jobDTO.getId());
        if (opt.isPresent()) {
            Job job1 = (Job) opt.get();
            job1.setSalary(jobDTO.getSalary());
            job1.setSubject(jobDTO.getSubject());
            job1.setSubject(jobDTO.getSubject());
            job1.setDescription(jobDTO.getDescription());
            job1.setType(jobDTO.getType());
            job1.setStatus(jobDTO.getStatus());
            job1.setResult(jobDTO.getResult());
            job1.setResponse_date(jobDTO.getResponse_date());
            job1.setInvited_user_id(jobDTO.getInvited_user_id());
            job1.setAccountId(jobDTO.getAccountId());
            job1.setAccount(accountRepository.findById(jobDTO.getAccountId()).get());
            job1.setFreelancerId(jobDTO.getFreelancerId());
            job1.setFreelancer(freelancerRepository.findById(jobDTO.getFreelancerId()).get());
            jobRepository.save(job1);
            return job1;
        }
        return null;
    }
}
