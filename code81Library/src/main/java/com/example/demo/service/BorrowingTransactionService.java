package com.example.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Book;
import com.example.demo.model.BorrowingTransaction;
import com.example.demo.model.Member;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowingTransactionRepository;
import com.example.demo.repository.MemberRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowingTransactionService {

    @Autowired
    private BorrowingTransactionRepository transactionRepository;

    public List<BorrowingTransaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Optional<BorrowingTransaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    public BorrowingTransaction borrowBook(BorrowingTransaction transaction) {
        transaction.setBorrowDate(LocalDate.now());
        transaction.setDueDate(LocalDate.now().plusWeeks(2));
        return transactionRepository.save(transaction);
    }

    public BorrowingTransaction returnBook(Long transactionId) {
        Optional<BorrowingTransaction> optional = transactionRepository.findById(transactionId);
        if (optional.isPresent()) {
            BorrowingTransaction tx = optional.get();
            tx.setReturnDate(LocalDate.now());
            return transactionRepository.save(tx);
        }
        throw new RuntimeException("Transaction not found");
    }
}
