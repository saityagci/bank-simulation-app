package com.cydeo.repository;

import com.cydeo.dto.TransactionDTO;
import com.cydeo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    @Query(value = "SELECT * FROM transactions ORDER BY creation_date DESC LIMIT 10",nativeQuery = true)

    List<Transaction> findLastTenTransactions();
    @Query("SELECT t FROM Transaction t WHERE t.sender.id=?1 OR t.receiver.id=?1")
    List<Transaction> findTransactionListById(Long id);
//    public static List<TransactionDTO> transactionDTOList = new ArrayList<>();
//    public TransactionDTO save(TransactionDTO transactionDTO){
//        transactionDTOList.add(transactionDTO);
//        return transactionDTO;
//    }
//
//    public List<TransactionDTO> findAll() {
//        return transactionDTOList;
//    }
//
//    public List<TransactionDTO> lastTransactions() {
//       return transactionDTOList.stream().sorted
//               (Comparator.comparing(TransactionDTO::getCreationDate).reversed()).limit(10)
//               .collect(Collectors.toList());
//    }
//
//    public List<TransactionDTO> findTransactionListById(Long id) {
//       return transactionDTOList.stream().filter(transactionDTO -> transactionDTO.getSender().equals(id)
//        || transactionDTO.getReceiver().equals(id)).collect(Collectors.toList());
//    }
}

