package com.foodorder.Online_Food_Order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodorder.Online_Food_Order.entity.Cart;
import com.foodorder.Online_Food_Order.entity.User;




	@Repository
	public interface CartRepository extends JpaRepository<Cart, Long> {
		
		void deleteCartByUser(User u);
	
}
