package com.example.freelancer.repository;

import com.example.freelancer.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface JobRepository extends JpaRepository<Job,Integer> {
    @Query("SELECT j FROM Job j WHERE j.freelancerId = :freelancerId AND j.status = 4")
    List<Job> getTotalJobDone(@Param(value="freelancerId") Integer freelancerId);

    @Query("SELECT j FROM Job j WHERE j.freelancerId = :freelancerId ORDER BY j.id DESC ")
    List<Job> getListJobByFreelancerId(Integer freelancerId);

    @Query("SELECT j FROM Job j WHERE j.accountId = :accountId ORDER BY j.id DESC ")
    List<Job> getListJobByAccountId(Integer accountId);

    @Query("SELECT j FROM Job j WHERE j.freelancerId = :freelancerId AND j.status = 4 ORDER BY j.id DESC ")
    List<Job> getListJobDoneByFreelancerId(Integer freelancerId);

    @Query("SELECT j FROM Job j WHERE j.accountId = :accountId AND j.status = 4 ORDER BY j.id DESC ")
    List<Job> getListJobDoneByAccountId(Integer accountId);

    @Query("SELECT j FROM Job j WHERE j.accountId = :accountId AND j.freelancerId = :freelancerId ORDER BY j.id DESC ")
    List<Job> getListJobByAccountIdAndFreelancerId(Integer accountId, Integer freelancerId);

    @Query("SELECT j FROM Job j ORDER BY created_at DESC")
    Page<Job> findAll(Pageable pageable);

    @Query("SELECT j FROM Job j WHERE j.status = :status")
    List<Job> findAllByStatus(int status);

//    @Query("SELECT DATE(m.updated_at),SUM(m.salary * 0.1) FROM Job m WHERE m.status = 4 AND m.updated_at BETWEEN :startDate and :endDate GROUP BY DATE(m.updated_at)")
    @Query("SELECT m.updated_at,SUM(m.salary * 0.1) FROM Job m WHERE m.status = 4 AND m.updated_at BETWEEN :startDate and :endDate GROUP BY m.updated_at")
    List<Object> getFinancial(Date startDate, Date endDate);
}
