package com.cydeo.repository;

import com.cydeo.dto.AccountDTO;
import com.cydeo.entity.Account;
import com.cydeo.enums.AccountStatus;
import com.cydeo.exceptions.RecordNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByAccountStatus(AccountStatus accountStatus);
//    public static List<AccountDTO> accountDTOList =new ArrayList<>();
//    public AccountDTO save(AccountDTO accountDTO){
//        accountDTOList.add(accountDTO);
//                return accountDTO;
//    }
//
//    public List<AccountDTO> findAll() {
//        return accountDTOList;
//    }
//
//    public AccountDTO findById(Long id) {
//        //write a method, that finds the account inside the list, if not throws
//        //RecordNotFoundException
//        return accountDTOList.stream().filter(account -> account.getId().equals(id))
//                .findAny().orElseThrow(()-> new RecordNotFoundException("Account not exist in database"));
//    }



}
