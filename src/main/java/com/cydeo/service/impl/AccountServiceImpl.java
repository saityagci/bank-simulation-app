package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.enums.AccountType;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Component

public class AccountServiceImpl implements AccountService {
   AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDTO createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
       AccountDTO accountDTO = new AccountDTO();
       return accountRepository.save(accountDTO);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        /*
            we are getting list of accounts from repository
            but, we need to return list of accountDTO to controller
            what we need to do is we will convert Account to AccountDTO
         */
        List<Account> accountList=accountRepository.findAll();
        return accountList;
    }

    @Override
    public void deleteAccount(Long id) {
        AccountDTO accountDTO =accountRepository.findById(id);
        accountDTO.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        return accountRepository.findById(id);
    }
}
