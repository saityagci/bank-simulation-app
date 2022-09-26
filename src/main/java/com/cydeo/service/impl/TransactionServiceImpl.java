package com.cydeo.service.impl;

import com.cydeo.exceptions.BadRequestException;
import com.cydeo.model.Account;
import com.cydeo.model.Transaction;
import com.cydeo.repository.AccountRepository;
import com.cydeo.service.TransactionService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Component
public class TransactionServiceImpl  implements TransactionService {

    AccountRepository accountRepository;

    public TransactionServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) {
        validateAccount(sender,receiver);
        return null;
    }

    private void validateAccount(Account sender, Account receiver) {
        /*
            -if any of the account is null
            -if account ids are the same(same account)
            -if the account exist in the database(repository)
         */
        if (sender==null || receiver==null){
            throw new BadRequestException("Sender or Reciver cannot be null");
        }
        if (sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver");
        }
        findAccountById(sender.getId());
        findAccountById(receiver.getId());
        

    }

    private Account findAccountById(UUID id) {
        return accountRepository.findById();

    }

    @Override
    public List<Transaction> findAllTransactions() {
        return null;
    }
}
