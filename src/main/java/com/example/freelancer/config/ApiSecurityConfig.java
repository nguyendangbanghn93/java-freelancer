package com.example.freelancer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AuthenticationProvider provider;

    private static final String PROTECT_URL = "/api/**";

    @Bean(name = "bCryptPasswordEncoder")
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher(PROTECT_URL)
                .addFilterBefore(authenticationFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/register*", "/login*").permitAll()
                .antMatchers("/api/test/admin").hasAnyRole("ADMIN")
        ;//, "/api/products*"
    }

    @Bean
    public AuthenticationFilter authenticationFilter() throws Exception {
        final AuthenticationFilter filter = new AuthenticationFilter(new AntPathRequestMatcher(PROTECT_URL));
        filter.setAuthenticationManager(this.authenticationManager());
        return filter;
    }
}
