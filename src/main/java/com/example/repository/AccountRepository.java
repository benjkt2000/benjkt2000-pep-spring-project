package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.lang.Integer;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findAccountByUsernameAndPassword(String username, String password);
}
