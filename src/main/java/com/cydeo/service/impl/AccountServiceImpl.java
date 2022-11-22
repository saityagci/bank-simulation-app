package com.cydeo.service.impl;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.mapper.AccountMapper;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.AccountService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component

public class AccountServiceImpl implements AccountService {
   AccountRepository accountRepository;
   AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public void createNewAccount(AccountDTO accountDTO) {
       // we will complete th DTO
        //convert it to entity and save it to database
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        accountDTO.setCreationDate(new Date());
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        /*
            we are getting list of accounts from repository
            but, we need to return list of accountDTO to controller
            what we need to do is we will convert Account to AccountDTO
         */
        List<Account> accountList=accountRepository.findAll();
        //list of account  converted to accountDto
       return accountList.stream().map(accountMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        //we need to find correct account based on id and change status to deleted
        Account account =accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        //find account return as DTO

        return accountMapper.convertToDto(accountRepository.findById(id).get());
    }
}
