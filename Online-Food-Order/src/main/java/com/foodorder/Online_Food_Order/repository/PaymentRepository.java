package com.foodorder.Online_Food_Order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodorder.Online_Food_Order.entity.Payment;


	@Repository
	public interface PaymentRepository extends JpaRepository<Payment, Long> {
			public List<Payment> findByOrderId(long orderId);
			
	
}
