package com.foodorder.Online_Food_Order.service;
import java.util.List;

import com.foodorder.Online_Food_Order.entity.Order;
import com.foodorder.Online_Food_Order.entity.TransactionDetails;


public interface OrderService {
	
		Order addOrder(Order order, long userId, long cartId);

		Order getOrderById(long orderId);

		Order updateOrder(Order order, long orderId);

		List<Order> getOrderByUserId(long userId);

		//List<Order> getAllOrders();

		// List<Order> getAllOrdersByCartId(long cartId);
		
		Order addOrderItem(Order order,long userId);
		
		void deleteOrder(long orderId);
		
		TransactionDetails createTransaction(Double amount);

		List<Order> getAllOrders();
	
}
