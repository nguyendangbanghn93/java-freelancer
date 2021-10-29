package com.example.freelancer.resdto;

import com.example.freelancer.dto.AccountDTO;
import com.example.freelancer.dto.CredentialDTO;
import com.example.freelancer.entity.Credential;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginAdminRes {
    private CredentialDTO credential;
    private AccountDTO account;
}
