package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.BorrowingTransaction;
import com.example.demo.service.BorrowingTransactionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/borrows")
public class BorrowingTransactionController {

    @Autowired
    
    private BorrowingTransactionService borrowService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<BorrowingTransaction> getAllTransactions() {
        return borrowService.getAllTransactions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public Optional<BorrowingTransaction> getTransactionById(@PathVariable Long id) {
        return borrowService.getTransactionById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public BorrowingTransaction borrowBook(@RequestBody BorrowingTransaction transaction) {
        return borrowService.borrowBook(transaction);
    }

    @PutMapping("/return/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public BorrowingTransaction returnBook(@PathVariable Long id) {
        return borrowService.returnBook(id);
    }
}