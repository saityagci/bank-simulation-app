package com.cydeo.service.impl;

import com.cydeo.enums.AccountType;
import com.cydeo.model.Account;
import com.cydeo.service.AccountService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AccountServiceImpl implements AccountService {
    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
       Account account=Account.builder()
               .id(UUID.randomUUID()).userId(userId).
               accountType(accountType).balance(balance).
               creationDate(creationDate)
               .build();
    }

    @Override
    public List<Account> listAllAccount() {
        return null;
    }
}
