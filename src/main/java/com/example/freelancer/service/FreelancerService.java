package com.example.freelancer.service;

import com.example.freelancer.dto.FreelancerDTO;
import com.example.freelancer.entity.Account;
import com.example.freelancer.entity.Freelancer;
import com.example.freelancer.repository.FreelancerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Configurable
public class FreelancerService {
    @Autowired
    FreelancerRepository freelancerRepository;
    public Freelancer register(FreelancerDTO freelancerDTO, Account account){
        Freelancer freelancer = new Freelancer();
        freelancer.setName(freelancerDTO.getName());
        freelancer.setAddress(freelancerDTO.getAddress());
        freelancer.setGender(freelancerDTO.getGender());
        freelancer.setPhone(freelancerDTO.getPhone());
        freelancer.setExperience(freelancerDTO.getExperience());
        freelancer.setDescription(freelancerDTO.getDescription());
        freelancer.setTitle(freelancerDTO.getTitle());
        freelancer.setAverageIncome(freelancerDTO.getAverageIncome());
        freelancer.setLanguage(freelancerDTO.getLanguage());
        freelancer.setRate(freelancerDTO.getRate());
        freelancer.setAccount(account);
        System.out.println(account.toString());
        return freelancerRepository.save(freelancer);
    }

    public Freelancer create(Freelancer freelancer, Account account) {
        freelancer.setAccount(account);
        return freelancerRepository.save(freelancer);
    }

    public List<Freelancer> getListFreelancer() {
        return freelancerRepository.findAll();
    }

    public Freelancer findById(@Nullable Integer id) {
        if (id == null) {
            return null;
        }
        if (freelancerRepository.existsById(id)) {
            return freelancerRepository.findById(id).get();
        }
        return null;
    }

    public Freelancer findByAccountId(@Nullable Integer id) {
        if (id == null) {
            return null;
        }

        Optional<Freelancer> optional = freelancerRepository.findByAccountId(id);
        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }
}
