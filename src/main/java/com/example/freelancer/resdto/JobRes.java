package com.example.freelancer.resdto;

import com.example.freelancer.dto.JobDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobRes {
    private List<JobDTO> list;
    private int totalPage;
}
