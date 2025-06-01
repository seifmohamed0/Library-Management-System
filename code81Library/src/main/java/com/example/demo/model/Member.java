package com.example.demo.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")

public class Member {
	 @Id @GeneratedValue
	 private Long id;

	 private String name;
	 private String email;

	 @OneToMany(mappedBy = "member")
	 private List<BorrowingTransaction> transactions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<BorrowingTransaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<BorrowingTransaction> transactions) {
		this.transactions = transactions;
	}


    
}
