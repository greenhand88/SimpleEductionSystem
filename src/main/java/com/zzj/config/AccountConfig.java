package com.zzj.config;

import com.example.blog.controller.AccountController;
import com.example.blog.dao.Mappers.AccountMapper;
import com.example.blog.entity.Account;
import com.example.blog.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan()
public class AccountConfig {
    /**
     * Entity
     *
     * @return
     */
    @Bean
    public Account getAccount() {
        return new Account();
    }

    /**
     * Service
     *
     * @return
     */
    @Bean
    public AccountService getAccountService() {
        return new AccountService();
    }

}
