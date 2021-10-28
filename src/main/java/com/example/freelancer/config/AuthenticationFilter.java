package com.example.freelancer.config;

import org.apache.el.parser.TokenMgrError;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class AuthenticationFilter  extends AbstractAuthenticationProcessingFilter {
    protected AuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String token = httpServletRequest.getHeader(AUTHORIZATION);
        if(token == null) {
            throw new AuthenticationCredentialsNotFoundException("Token not found!");
        }
        try {
            token = token.replaceAll("Bearer", "").trim();
            Authentication authentication = new UsernamePasswordAuthenticationToken(token, token);
            return getAuthenticationManager().authenticate(authentication);
        } catch (Exception e) {
            throw new TokenMgrError("Token Error", 0);
        }
    }

    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

}
