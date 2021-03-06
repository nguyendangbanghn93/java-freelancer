package com.example.freelancer.controller;

import com.example.freelancer.dto.FreelancerDTO;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.service.FreelancerService;
import com.example.freelancer.service.JobService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/v1/freelancers")
@CrossOrigin
public class FreelancerDataController {
    @Autowired
    FreelancerService freelancerService;

    @Autowired
    JobService jobService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseAPI<List<FreelancerDTO>> getListFreelancerPagination(
            @RequestParam @Nullable Integer currentPage,
            @RequestParam @Nullable Integer pageSize
    ) {
        List<FreelancerDTO> list = freelancerService.getListFreelancer().stream().map(x -> x.toFreelancerDTO()).collect(Collectors.toList());
        list.forEach((el) -> {
            el.setTotalEarning(jobService.getTotalEarning(el.getId()));
            el.setTotalJobDone(jobService.getTotalJobDone(el.getId()));
        });
        return new ResponseAPI<List<FreelancerDTO>>(list, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseAPI<FreelancerDTO> getFreelancerById(
            @PathVariable(value = "id") @Nullable Integer id
    ) {
        Freelancer freelancer = freelancerService.findById(id);
        if (freelancer == null) {
            return new ResponseAPI<FreelancerDTO>(null, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }

        FreelancerDTO freelancerDTO = freelancer.toFreelancerDTO();
        freelancerDTO.setTotalJobDone(jobService.getTotalJobDone(freelancer.getId()));
        freelancerDTO.setTotalEarning(jobService.getTotalEarning(freelancer.getId()));
        return new ResponseAPI<FreelancerDTO>(freelancerDTO, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
    }
}
