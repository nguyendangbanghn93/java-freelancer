package com.example.freelancer.resdto;

import com.example.freelancer.dto.AccountDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRes {
    private List<AccountDTO> list;
    private int totalPage;
}
