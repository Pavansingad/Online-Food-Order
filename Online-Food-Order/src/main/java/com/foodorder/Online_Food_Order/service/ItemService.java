package com.foodorder.Online_Food_Order.service;

import java.util.List;



import com.foodorder.Online_Food_Order.entity.Category;
import com.foodorder.Online_Food_Order.entity.Item;
import com.foodorder.Online_Food_Order.entity.ItemPaging;





public interface ItemService {
	Item addItem(Item item);
    List<Item> getAllItems();
	Item getItemByItemId(long itemId);
	Item updateItem(Item item, long itemId);
	void deleteItem(long itemId);
	List<Item> findByCategory(Category category);
	ItemPaging findByCategory(Category category, Integer pageNo, Integer pageSize);
	ItemPaging getAllItems(Integer pageNo, Integer pageSize);
	List<Item> findByMrpPrice(double mrpPrice);
	ItemPaging findByItemname(String keyword, Integer pageNo, Integer pageSize);
}
