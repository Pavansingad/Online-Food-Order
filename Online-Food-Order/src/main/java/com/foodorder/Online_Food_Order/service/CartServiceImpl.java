package com.foodorder.Online_Food_Order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.foodorder.Online_Food_Order.entity.Cart;
import com.foodorder.Online_Food_Order.entity.Item;
import com.foodorder.Online_Food_Order.entity.User;
import com.foodorder.Online_Food_Order.exception.ResourceNotFoundException;
import com.foodorder.Online_Food_Order.repository.CartRepository;






	@Service
	public class CartServiceImpl implements CartService {
		
		@Autowired
		public CartRepository cartRepository;
		
//		@Autowired
//		public ProductRepository productRepository;

		@Autowired
		public ItemService itemService;
		
		@Autowired
		public UserService userService;
		
	public CartServiceImpl(CartRepository cartRepository) {
			super();
			this.cartRepository = cartRepository;
		}

	@Override
	public Cart addCart(Cart cart, long itemId, long userId) {
		Item item =itemService.getItemByItemId(itemId) ;
		User user =userService.getUserById(userId);
		 List<Cart> crl = this.getAllCarts();
		 int flag = 0;
		 Cart existingCart = null;
		 if (crl.size() > 0) {
			 for (int i=0;i< crl.size();i++) {
				 Cart c = this.getCartById(crl.get(i).getCartId());
				 if (c.getUser().getUserId() == userId && c.getItem().getItemId() == itemId) {
					 flag = 1;
					 existingCart = c;
				 }
			 }
		 }
		 item.setQuantity(item.getQuantity()-cart.getQuantity());
		 if (flag ==1 && existingCart != null) {
			 existingCart.setQuantity(existingCart.getQuantity() + cart.getQuantity());
			 existingCart.setMrpPrice(item.getMrpPrice());
			existingCart.setUser(user);
			System.out.println("111111111111111111111111111111111");
			return this.updateCart(existingCart, existingCart.getCartId());
		 } else {
			 cart.setItem(item);
			cart.setMrpPrice(item.getMrpPrice());
			cart.setUser(user);
			System.out.println("2222222222222222222222222222222222222222");
			return cartRepository.save(cart);
		 }
	}

	@Override
	public List<Cart> getAllCarts() {
		return cartRepository.findAll();
	}

	@Override
	public Cart getCartById(long cartId) {
		return cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
	}

	@Override
	public Cart updateCart(Cart cart, long cartId) {
		Cart existingCart=cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
		existingCart.setQuantity(cart.getQuantity());
		//existingCart.setPrice(cart.getPrice());
		existingCart.setMrpPrice(cart.getMrpPrice());
		//existingCart.setImage(cart.getImage());
		existingCart.setCartId(cart.getCartId());
		existingCart.setItem(cart.getItem());
		//existingCart.setCustomerId(cart.getCustomerId());
		existingCart.setUser(cart.getUser());
	    cartRepository.save(existingCart);
	    
		return existingCart;
	}

	@Override
	public void deleteCart(long cartId) {
		Cart existingCart=cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
		Item item =itemService.getItemByItemId(existingCart.getItem().getItemId());
		item.setQuantity(item.getQuantity());
		itemService.updateItem(item, item.getItemId());
		// cartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","Id",cartId));
		cartRepository.deleteById(cartId);
		
		
	}

	@Override
	public void deleteCartByUser(User u) {
		// TODO Auto-generated method stub
		
	}

}
