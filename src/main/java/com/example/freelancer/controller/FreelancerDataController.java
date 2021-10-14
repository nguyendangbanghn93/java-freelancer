package com.example.freelancer.controller;

import com.example.freelancer.dto.FreelancerDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.resdto.FreelancerRes;
import com.example.freelancer.resdto.ResponseAPI;
import com.example.freelancer.service.AccountService;
import com.example.freelancer.service.FreelancerService;
import com.example.freelancer.util.APIMessage;
import com.example.freelancer.util.APIStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/freelancers")
@CrossOrigin
public class FreelancerDataController {
    @Autowired
    FreelancerService freelancerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseAPI<FreelancerRes> getListFreelancerPagination(
            @RequestParam @Nullable Integer currentPage,
            @RequestParam @Nullable Integer pageSize
    ) {
        Page<Freelancer> listPage = freelancerService.getListFreelancer(currentPage, pageSize);

        FreelancerRes freelancerRes = new FreelancerRes();
        freelancerRes.setList(listPage.stream().map(x -> x.toFreelancerDTO()).collect(Collectors.toList()));
        freelancerRes.setTotalPage(listPage.getTotalPages());

        return new ResponseAPI<FreelancerRes>(freelancerRes, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseAPI<FreelancerDTO> getFreelancerById(
            @PathVariable(value = "id") @Nullable Integer id
    ) {
        Freelancer freelancer = freelancerService.findById(id);
        if (freelancer == null) {
            return new ResponseAPI<FreelancerDTO>(null, APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
        }
        return new ResponseAPI<FreelancerDTO>(freelancerService.findById(id).toFreelancerDTO(), APIMessage.MES_SUCCESS, APIStatusCode.SUCCESS);
    }
}
