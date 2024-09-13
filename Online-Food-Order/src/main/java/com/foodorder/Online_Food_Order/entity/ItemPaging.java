package com.foodorder.Online_Food_Order.entity;

import java.util.List;


public class ItemPaging {
	private List<Item> item;
	private long totalItem;
	public List<Item> getItem() {
		return item;
	}
	public void setItem(List<Item> item) {
		this.item = item;
	}
	public long getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(long totalItem) {
		this.totalItem = totalItem;
	}
	

}
