package com.douglas.thriftstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.douglas.thriftstore.model.TransactionDetails;

public interface TransactionRepository extends JpaRepository<TransactionDetails, Long> {

}
