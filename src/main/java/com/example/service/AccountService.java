package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    AccountRepository accountRepository;
    
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account persistAccount(Account account) {
        if(account.getUsername().trim().length() <= 0 || account.getPassword().length() < 4) {
            return null;
        }

        return this.accountRepository.save(account);
    }

    public Account loginAccount(Account account) { 
        Account existingAccount = this.accountRepository.findAccountByUsernameAndPassword(account.getUsername(), account.getPassword());

        // if(existingAccount == null)
        //     return null;

        return existingAccount;
    }
}
