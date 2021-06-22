package com.example.asmAuth.config;


import com.example.asmAuth.entity.Account;
import com.example.asmAuth.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private AccountService accountService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }
    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        Object credentials = usernamePasswordAuthenticationToken.getCredentials();
        if(credentials == null) {
            throw new UsernameNotFoundException("Credential not found!");
        }
        String accessToken = String.valueOf(credentials);
        Account account = accountService.findByToken(accessToken);
        if(account == null) {
            return null;
        }
        UserDetails userDetails = User.builder()
                .username(account.getUsername())
                .password(account.getPasswordHash())
                .roles(account.getRoleName())
                .build();
        System.out.println(userDetails.getUsername());
        return userDetails;

    }
}
