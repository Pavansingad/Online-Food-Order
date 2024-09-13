package com.foodorder.Online_Food_Order.service;

import java.util.List;

import com.foodorder.Online_Food_Order.entity.Cart;
import com.foodorder.Online_Food_Order.entity.User;




public interface CartService {
	Cart addCart(Cart cart,long itemId,long userId);
	List<Cart> getAllCarts();
	Cart getCartById(long cartId);
	Cart updateCart(Cart cart, long cartId);
	void deleteCart(long cartId);
	void deleteCartByUser(User u);
}
