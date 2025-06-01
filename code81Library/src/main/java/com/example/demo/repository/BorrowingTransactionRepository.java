package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Book;
import com.example.demo.model.BorrowingTransaction;
import com.example.demo.model.Member;

public interface BorrowingTransactionRepository extends JpaRepository<BorrowingTransaction, Long> {
    boolean existsByMemberAndBookAndReturnedFalse(Member member, Book book);

}