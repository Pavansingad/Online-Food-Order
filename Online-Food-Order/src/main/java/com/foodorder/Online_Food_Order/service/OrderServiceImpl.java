package com.foodorder.Online_Food_Order.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.foodorder.Online_Food_Order.entity.Cart;
import com.foodorder.Online_Food_Order.entity.Order;
import com.foodorder.Online_Food_Order.entity.TransactionDetails;
import com.foodorder.Online_Food_Order.entity.User;
import com.foodorder.Online_Food_Order.exception.ResourceNotFoundException;
import com.foodorder.Online_Food_Order.repository.CartRepository;
import com.foodorder.Online_Food_Order.repository.OrderRepository;
import com.razorpay.RazorpayClient;

	@Transactional
	@Service
	public class OrderServiceImpl implements OrderService {
		
		private static final String ORDER_PLACED = "Placed";

	    private static final String KEY = "rzp_test_VakOH2Mblf4grH";
	    private static final String KEY_SECRET = "XyJb6uzrzJEf8bYTRnjHdAsi";
	    private static final String CURRENCY = "INR";

		@Autowired
		public OrderRepository orderRepository;

		@Autowired
		public ItemService itemService;

		@Autowired
		public CartService cartService;

		@Autowired
		private UserService userService;

		@Autowired
		private CartRepository c;

		public OrderServiceImpl(OrderRepository orderRepository, ItemService itemService, CartService cartService,
				UserService userService) {
			super();
			this.orderRepository = orderRepository;
			this.itemService = itemService;
			// this.cartService = cartService;
			this.userService = userService;
		}

		@Override
		public Order addOrder(Order order, long userId, long cartId) {
			// Cart cart=cartService.getCartById(cartId);
			// Product product=productService.getProductByProductId(productId);
			Cart cart = cartService.getCartById(cartId);
			// order.setCart(cart);
			// System.out.println("cart"+cart);
			User user = userService.getUserById(userId);
			// order.setPrice(cartId);
			order.setTotalPrice(order.getMrpPrice() * cart.getQuantity());
			order.setPaymentStatus(order.getPaymentStatus());
			order.setOrderStatus(order.getOrderStatus());
			order.setOrderedDate(order.getOrderedDate());
			order.setMrpPrice(cart.getMrpPrice());
			order.setQuantity(cart.getQuantity());
			System.out.println(">>>>>" + cart.getQuantity());
			order.setUser(user);
			// order.setCartId(order.getCartId());
			// order.setTotalPrice(order.getTotalPrice());
			Order o = orderRepository.save(order);
			c.deleteById(cartId);
			return o;
		}

		@Override
		public Order getOrderById(long orderId) {
			return orderRepository.findById(orderId)
					.orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));

		}

		@Override
		public Order updateOrder(Order order, long orderId) {
			Order existingOrder = orderRepository.findById(orderId)
					.orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));
			existingOrder.setTotalPrice(order.getMrpPrice());
			// existingOrder.setPrice(order.getPrice());
			existingOrder.setPaymentStatus(order.getPaymentStatus());
			existingOrder.setMrpPrice(order.getMrpPrice());
			existingOrder.setOrderStatus(order.getOrderStatus());
			existingOrder.setUser(order.getUser());
			// existingOrder.setCartId(order.getCartId());
			existingOrder.setOrderedDate(order.getOrderedDate());
			// existingOrder.setCart(order.getCart());
			orderRepository.save(existingOrder);
			return existingOrder;
		}

		@Override
		public List<Order> getOrderByUserId(long userId) {
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			java.util.Date date = new java.util.Date();
			String currentDate = sdf.format(date);
			String[] array = currentDate.split("/");
			int month = Integer.parseInt(array[0]);
			int day = Integer.parseInt(array[1]);
			int year = Integer.parseInt(array[2]);
			java.util.Date d = new java.util.Date(month, day, year);
			System.out.println(d);
			List<Order> orders = orderRepository.findByUserUserId(userId);
			System.out.println(orders);
			return orderRepository.findByUserUserId(userId);
		}

//		@Override
//		public List<Order> getAllOrders() {
//			// TODO Auto-generated method stub
//			return null;
//		}

		@Override
		public Order addOrderItem(Order order, long userId) {
			User user = userService.getUserById(userId);
			order.setTotalPrice(order.getTotalPrice());
			order.setPaymentStatus(order.getPaymentStatus());
			order.setOrderStatus(order.getOrderStatus());
			order.setOrderedDate(order.getOrderedDate());
			order.setUser(user);
			order.setItem(order.getItem());
			System.out.println("################"+ order.getItem().get(0).getQuantity());
			Order o = orderRepository.save(order);
			return o;
		}

		@Override
		public void deleteOrder(long orderId) {
			orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order", "Id", orderId));
			orderRepository.deleteById(orderId);

			
		}

		@Override
		public TransactionDetails createTransaction(Double amount) {
			try {

	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("amount", (amount * 100) );
	            jsonObject.put("currency", CURRENCY);

	            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);

	            com.razorpay.Order order = razorpayClient.orders.create(jsonObject);

	            TransactionDetails transactionDetails =  prepareTransactionDetails(order);
	            return transactionDetails;
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return null;
	    }

	    private TransactionDetails prepareTransactionDetails(com.razorpay.Order order) {
	        String orderId = order.get("id");
	        String currency = order.get("currency");
	        Integer amount = order.get("amount");

	        TransactionDetails transactionDetails = new TransactionDetails(orderId, currency, amount, KEY);
	        return transactionDetails;
	    }

		@Override
		public List<Order> getAllOrders() {
		
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				java.util.Date date = new java.util.Date();
				String currentDate = sdf.format(date);
				String[] array = currentDate.split("/");
				int month = Integer.parseInt(array[0]);
				int day = Integer.parseInt(array[1]);
				int year = Integer.parseInt(array[2]);
				java.util.Date d = new java.util.Date(month, day, year);
				System.out.println(d);
				List<Order> orders = orderRepository.findAll();
				System.out.println(orders);
				return orderRepository.findAll();
			}

			
		
		

}
