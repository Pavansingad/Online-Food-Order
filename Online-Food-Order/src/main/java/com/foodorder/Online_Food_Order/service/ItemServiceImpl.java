package com.foodorder.Online_Food_Order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.foodorder.Online_Food_Order.entity.Category;
import com.foodorder.Online_Food_Order.entity.Item;
import com.foodorder.Online_Food_Order.entity.ItemPaging;
import com.foodorder.Online_Food_Order.exception.ResourceNotFoundException;
import com.foodorder.Online_Food_Order.repository.ItemRepository;



	@Service
	public class ItemServiceImpl  implements ItemService{
		@Autowired
		private ItemRepository itemRepository;

		@Override
		public Item addItem(Item item) {
			System.out.println("item added Succesfully "+item);
			item.setItemname(item.getItemname());
			item.setQuantity(item.getQuantity());
			item.setMrpPrice(item.getMrpPrice());
		
			item.setDescription(item.getDescription());
				return itemRepository.save(item);
			}


		@Override
		public List<Item> getAllItems() {			
				return itemRepository.findAll();
			}	
		@Override
		public Item getItemByItemId(long itemId) {
			return itemRepository.findById(itemId).orElseThrow(()->new ResourceNotFoundException("Item","Id",itemId));
		}

		

		@Override
		public Item updateItem(Item item, long itemId) {
			Item existingItem = itemRepository.findById(itemId).orElseThrow(()->new ResourceNotFoundException("item","itemId",itemId));
			existingItem.setItemname(item.getItemname());
			existingItem.setMrpPrice(item.getMrpPrice());
			existingItem.setImage(item.getImage());
			existingItem.setDescription(item.getDescription());
		
			existingItem.setQuantity(item.getQuantity());

			itemRepository.save(existingItem);	
			return existingItem;		
		}

		@Override
		public void deleteItem(long itemId) {
			itemRepository.findById(itemId).orElseThrow(()->new ResourceNotFoundException("item","Id",itemId));
			itemRepository.deleteById(itemId);	
			
		}

		@Override
		public List<Item> findByCategory(Category category) {
			return itemRepository.findByCategory(category);
		}

		@Override
		public ItemPaging findByCategory(Category category, Integer pageNo, Integer pageSize) {
			Pageable paging = PageRequest.of(pageNo, pageSize);
			Page<Item> pageResult = itemRepository.findByCategory(category, paging);
			ItemPaging pr = new ItemPaging();
			pr.setTotalItem(pageResult.getTotalElements());
			if(pageResult.hasContent()) {
	            pr.setItem(pageResult.getContent());
	        } else {
	        	 pr.setItem(new ArrayList<Item>());
	        }
			return pr;
		}

		@Override
		public ItemPaging getAllItems(Integer pageNo, Integer pageSize) {
			Pageable paging = PageRequest.of(pageNo, pageSize);
			Page<Item> pageResult = itemRepository.findAll(paging);
			ItemPaging pr = new ItemPaging();
			pr.setTotalItem(pageResult.getTotalElements());
			System.out.println(">>>>>"+ pageResult.getTotalPages());
			if(pageResult.hasContent()) {
	            pr.setItem(pageResult.getContent());
	        } else {
	        	 pr.setItem(new ArrayList<Item>());
	        }
			return pr;
		}

		@Override
		public List<Item> findByMrpPrice(double mrpPrice) {
			return itemRepository.findByMrpPrice(mrpPrice);
		}
		
		@Override
		public ItemPaging findByItemname(String keyword, Integer pageNo, Integer pageSize) {
			Pageable paging = PageRequest.of(pageNo, pageSize);
			Page<Item> pageResult = itemRepository.findByItemnameContains(keyword, paging);
			ItemPaging pr = new ItemPaging();
			pr.setTotalItem(pageResult.getTotalElements());
			if(pageResult.hasContent()) {
	            pr.setItem(pageResult.getContent());
	        } else {
	        	 pr.setItem(new ArrayList<Item>());
	        }
			return pr;
		}
		
}
