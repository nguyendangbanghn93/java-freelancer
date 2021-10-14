package com.example.freelancer.resdto;

import com.example.freelancer.dto.FreelancerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FreelancerRes {
    private List<FreelancerDTO> list;
    private int totalPage;
}
