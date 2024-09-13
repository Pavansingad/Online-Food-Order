package com.foodorder.Online_Food_Order.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodorder.Online_Food_Order.entity.Order;
import com.foodorder.Online_Food_Order.entity.Payment;
import com.foodorder.Online_Food_Order.entity.User;
import com.foodorder.Online_Food_Order.exception.ResourceNotFoundException;
import com.foodorder.Online_Food_Order.repository.OrderRepository;
import com.foodorder.Online_Food_Order.repository.PaymentRepository;





	@Service
	public class PaymentServiceImpl implements PaymentService{
		
		@Autowired
	private PaymentRepository paymentRepository;
		
		@Autowired
		private OrderRepository orderRepository;

		
		@Autowired
		private UserService userService;
		
		@Autowired
		private OrderService orderService;


	        public PaymentServiceImpl(PaymentRepository paymentRepository, ItemService itemService,
				UserService userService,OrderService orderService) {
			super();
			this.paymentRepository = paymentRepository;
			
			this.userService = userService;
			this.orderService = orderService;

		}


			@Override
			public Payment addPayment(Payment payment, long orderId, long userId) {
				// TODO Auto-generated method stub
	        	Order order = orderRepository.findById(orderId)
						.orElseThrow(() -> new ResourceNotFoundException("Order", "orderId", orderId));
				System.out.println("****************"+order.getOrderId());
	        	payment.setOrderId(orderId);
				payment.setTotalPrice(order.getTotalPrice());
				payment.setPaidDate(LocalDate.now());
				payment.setPaidAmount(order.getTotalPrice());
				if (payment.getTotalPrice() == payment.getPaidAmount()) {
					order.setPaymentStatus("PAID");
					order.setOrderStatus("Delivered");
				} else {

					order.setPaymentStatus("NOT-PAID");
					order.setOrderStatus("payment pending");
				}
					  User user=userService.getUserById(userId);
				    	
				    	payment.setUser(user);
				    	
				    	
				    	     //return paymentRepository.save(payment);
				    	
				
				return paymentRepository.save(payment);
				
	        }
	              // order = orderService.getOrderById(orderId);
	        	//payment.setOrderId(order.getOrderId());
	        	//payment.setTotalPrice(payment.getTotalPrice());
	        	//payment.setPaidDate(payment.getPaidDate());
	  

			


			@Override
			public List<Payment> getAllPayments() {
				return paymentRepository.findAll();
			}


			@Override
			public Payment getPaymentById(long paymentId) {
				return paymentRepository.findById(paymentId).orElseThrow(()->new ResourceNotFoundException("Payement","Id",paymentId));
			}

			


			@Override
			public void deletePayment(long paymentId) {
				paymentRepository.findById(paymentId).orElseThrow(()->new ResourceNotFoundException("Payement","Id",paymentId));
				paymentRepository.deleteById(paymentId);
				
				
			}


			@Override
			public List<Payment> getAllPaymentsByUserId(long userId) {
				return paymentRepository.findByOrderId(userId);
			}
}
