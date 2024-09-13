package com.foodorder.Online_Food_Order.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.foodorder.Online_Food_Order.entity.Category;
import com.foodorder.Online_Food_Order.entity.Item;




	@Repository 
	public interface ItemRepository  extends JpaRepository<Item, Long>, PagingAndSortingRepository<Item, Long> {
		
		public List<Item> findByItemId(long itemId);
		public List<Item> findByCategory(Category category);
		public Page<Item> findByCategory(Category category, Pageable page);
		@Query("select b from Item b where b.mrpPrice = :mrpPrice")
		public List<Item> findByMrpPrice(double mrpPrice);
		public Page<Item> findByItemnameContains(String keyword, Pageable page);
	
}
