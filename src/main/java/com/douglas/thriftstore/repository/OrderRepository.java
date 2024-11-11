package com.douglas.thriftstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.douglas.thriftstore.model.Orders;


@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    // No additional methods needed for table creation
	List<Orders> findByUserId(String userid);
	List<Orders> findBySellerId(String userid);
	Orders findByOrderId(Long id);
}