package com.foodorder.Online_Food_Order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.foodorder.Online_Food_Order.entity.Category;
import com.foodorder.Online_Food_Order.entity.Item;
import com.foodorder.Online_Food_Order.entity.ItemPaging;
import com.foodorder.Online_Food_Order.service.ItemService;

import jakarta.validation.Valid;
@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api/items")
public class ItemController {


	
	@Autowired
	private ItemService itemService;

	
	//to add product to cart
		@PostMapping("/additems")
		public ResponseEntity<Item> addItem(@Valid @RequestBody Item item) {

			return new ResponseEntity<Item>(itemService.addItem(item), HttpStatus.CREATED);
		}

		// to get all products
		@GetMapping
		public List<Item> getAllItems() {
			return itemService.getAllItems();
		}

		// to get product by cart id
		@GetMapping("items/{itemId}")
		public ResponseEntity<Item> getItemById(@PathVariable("itemId") long itemId) {
			return new ResponseEntity<Item>(itemService.getItemByItemId(itemId), HttpStatus.OK);
		}

		// to update product
		@PutMapping("{itemId}")
		public ResponseEntity<Item> updateItem(@Valid @PathVariable("itemId") long itemId, @RequestBody Item item) {
			return new ResponseEntity<Item>(itemService.updateItem(item,itemId), HttpStatus.OK);
		}

		@DeleteMapping("{itemId}")
		public ResponseEntity<Boolean> deleteItem(@PathVariable("itemId") long itemId) {
			itemService.deleteItem(itemId);
			boolean flag = true;
			return new ResponseEntity<Boolean>(flag, HttpStatus.OK);
		}
		
		@GetMapping("/{categoryId}")
		public List<Item> getAllItemsByCategory(@PathVariable("categoryId") int categoryId) {
			Category c = Category.valueOf(categoryId);
			return itemService.findByCategory(c);
		}
		
		@GetMapping("/{categoryId}/{pageNo}/{pageSize}")
		public ItemPaging getAllItemsByCategory(@PathVariable("categoryId") int categoryId, @PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
			Category c = Category.valueOf(categoryId);
			return itemService.findByCategory(c, pageNo, pageSize);
		}
		
		@GetMapping("/{pageNo}/{pageSize}")
		public ItemPaging getAllBooks(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
			return itemService.getAllItems(pageNo, pageSize);
		}
		
		@GetMapping("/mrp/{mrpPrice}")
		public List<Item> getByMRPPrice(@PathVariable("mrpPrice") double mrpPrice) {
			return itemService.findByMrpPrice(mrpPrice);
		}
		
		@GetMapping("/itemSearch/{keyword}/{pageNo}/{pageSize}")
		public ItemPaging getBookByName(@PathVariable("keyword") String keyword,
				@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
			return itemService.findByItemname(keyword, pageNo, pageSize);
		}
}

