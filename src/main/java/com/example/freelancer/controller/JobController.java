package com.example.freelancer.controller;

import com.example.freelancer.dto.FreelancerDTO;
import com.example.freelancer.dto.JobDTO;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.repository.JobRepository;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.service.JobService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/job")
@CrossOrigin
public class JobController {
    @Autowired
    JobService jobService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public JobDTO getDetailJob(
            @PathVariable(value = "id") @Nullable Integer id
    ) {
        return jobService.getDetailJob(id).toJobDTO();
    }
}
