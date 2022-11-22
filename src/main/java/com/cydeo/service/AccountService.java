package com.cydeo.service;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountType;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    void createNewAccount(AccountDTO accountDTO);
    List<AccountDTO> listAllAccount();

    void deleteAccount(Long id);

    AccountDTO retrieveById(Long id);
}
