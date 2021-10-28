package com.example.freelancer.controller;

import com.example.freelancer.dto.JobDTO;
import com.example.freelancer.dto.MailDTO;
import com.example.freelancer.entity.Job;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.service.JobService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class JobController {
    @Autowired
    JobService jobService;

    @RequestMapping(method = RequestMethod.GET, value = "/api/job/{id}")
    public JobDTO getDetailJob(
            @PathVariable(value = "id") @Nullable Integer id
    ) {
        System.out.println(id);
        return jobService.getDetailJob(id).toJobDTO();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/job")
    public ResponseAPI<JobDTO> createJob(
            @RequestBody JobDTO jobDTO
    ) {
        Job job = jobService.createJob(jobDTO);
        if (job != null) {
            return new ResponseAPI(job.toJobDTO(), APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }
//        MailController mailController = new MailController();
//        MailDTO mailDTO = new MailDTO();
//        mailDTO.setTitle("title");
//        mailDTO.setBody("<h1>xxx</h1>");
//        mailDTO.setReceiver("minhduc95.hl@gmail.com");
//        mailController.sendHtmlEmail(mailDTO);
        return new ResponseAPI(APIMessage.MES_ERROR, APIStatusCode.ERROR);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/api/job/update")
    public ResponseAPI<JobDTO> updateJob(
            @RequestBody JobDTO jobDTO
    ) {
        Job job = jobService.updateJob(jobDTO);
        if (job != null) {
            return new ResponseAPI(job.toJobDTO(), APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }
        return new ResponseAPI(APIMessage.MES_ERROR, APIStatusCode.ERROR);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/job/list")
    public ResponseAPI<List<JobDTO>> listJob(
            @RequestParam @Nullable Integer freelancerId,
            @RequestParam @Nullable Integer accountId
    ) {
        List<JobDTO> list = jobService.getListJob().stream().map(x -> x.toJobDTO()).collect(Collectors.toList());
        if (freelancerId != null) {
            list = jobService.getListJobByFreelancerId(freelancerId).stream().map(x -> x.toJobDTO()).collect(Collectors.toList());
        }
        if (accountId != null) {
            list = jobService.getListJobByAccountId(accountId).stream().map(x -> x.toJobDTO()).collect(Collectors.toList());
        }
        if (freelancerId != null && accountId != null) {
            list = jobService.getListJobByAccountIdAndFreelancerId(accountId, freelancerId).stream().map(x -> x.toJobDTO()).collect(Collectors.toList());
        }

        return new ResponseAPI<List<JobDTO>>(list, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
    }
}
