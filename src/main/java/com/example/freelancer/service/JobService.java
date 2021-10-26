package com.example.freelancer.service;

import com.example.freelancer.dto.JobDTO;
import com.example.freelancer.dto.TransactionHistoryDTO;
import com.example.freelancer.entity.Job;
import com.example.freelancer.entity.SystemConfig;
import com.example.freelancer.repository.AccountRepository;
import com.example.freelancer.repository.FreelancerRepository;
import com.example.freelancer.repository.JobRepository;
import com.example.freelancer.repository.SystemConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

    @Autowired
    SystemConfigRepository systemConfigRepository;

    @Autowired
    TransactionService transactionService;

    public Job getDetailJob(Integer id) {
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
            job.setCreated_at(new Date());
            job.setUpdated_at(new Date());
            job.setRate(jobDTO.getRate());
            job.setComment(jobDTO.getComment());
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
            job1.setUpdated_at(new Date());
            job1.setRate(jobDTO.getRate());
            job1.setComment(jobDTO.getComment());

            if (job1.getStatus() == 2 || job1.getStatus() == 5) {
                Optional<SystemConfig> optionalSystemConfig = systemConfigRepository.findById(1);
                SystemConfig systemConfig = new SystemConfig();
                TransactionHistoryDTO transactionHistoryDTO = new TransactionHistoryDTO();

                systemConfig.setAmount(0);
                if (optionalSystemConfig.isPresent()) {
                    systemConfig = optionalSystemConfig.get();
                }
                double systemConfigAmount = systemConfig.getAmount();

                if (job1.getStatus() == 2) {
                    systemConfig.setAmount(systemConfigAmount + job1.getSalary());
                    transactionHistoryDTO.setType(2); // charge
                } else {
                    if (systemConfigAmount - job1.getSalary() < 0) {
                        return null;
                    }
                    systemConfig.setAmount(systemConfigAmount - job1.getSalary());
                    transactionHistoryDTO.setType(1); // withdraw
                }
                transactionHistoryDTO.setAmount(job1.getSalary());
                transactionHistoryDTO.setAccountId(job1.getAccountId());
                transactionHistoryDTO.setAccount(job1.getAccount());

                transactionService.createTransactionHistory(transactionHistoryDTO);
                systemConfigRepository.save(systemConfig);
            }
            jobRepository.save(job1);
            return job1;
        }
        return null;
    }

    public int getTotalJobDone(int freelancerId) {
        return jobRepository.getTotalJobDone(freelancerId).size();
    }

    public double getTotalEarning(int freelancerId) {
        List<Job> lst = jobRepository.getTotalJobDone(freelancerId);
        Double sum = (double) 0;
        for (int i = 0; i < lst.size(); i++) {
            sum += lst.get(i).getSalary();
        }
        return sum;
    }

    public List<Job> getListJob() {
        return jobRepository.findAll();
    }

    public List<Job> getListJobByFreelancerId(int freelancerId) {
        return jobRepository.getListJobByFreelancerId(freelancerId);
    }

    public List<Job> getListJobByAccountId(Integer accountId) {
        return jobRepository.getListJobByAccountId(accountId);
    }

    public List<Job> getListJobByAccountIdAndFreelancerId(Integer accountId, Integer freelancerId) {
        return jobRepository.getListJobByAccountIdAndFreelancerId(accountId, freelancerId);
    }
}
