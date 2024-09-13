package com.foodorder.Online_Food_Order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
@Entity 
@Table(name="item_table")
@NamedQuery(name = "book.findByMrpPrice", query = "select b from Item b where b.mrpPrice = :mrpPrice")
@SequenceGenerator(name = "generator2", sequenceName = "gen2", initialValue = 5000)
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator2")
	@Column(name="item_id")
	private long itemId;
	
	@NotEmpty(message = "Item name is required.")
    @Column(name = "itemname", nullable = false, length =30)
	private String itemname;

	@Column(name="item_image")
	@NotEmpty
	private String image;
	
	@NotEmpty(message = "Item description is required.")
	@Column(name = "description", nullable = false)
	private String description;	
	
	@Column(name = "mrp_price", nullable = false)
    private double mrpPrice;
	
	@Column(name = "quantity")
	private long quantity;
	
	
	private Category category;


	public Item(long itemId, @NotEmpty(message = "Item name is required.") String itemname, @NotEmpty String image,
			@NotEmpty(message = "Item description is required.") String description, double mrpPrice, long quantity,
			Category category) {
		super();
		this.itemId = itemId;
		this.itemname = itemname;
		this.image = image;
		this.description = description;
		this.mrpPrice = mrpPrice;
		this.quantity = quantity;
		this.category = category;
	}


	public Item() {
		super();
	}


	public long getItemId() {
		return itemId;
	}


	public void setItemId(long itemId) {
		this.itemId = itemId;
	}


	public String getItemname() {
		return itemname;
	}


	public void setItemname(String itemname) {
		this.itemname = itemname;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getMrpPrice() {
		return mrpPrice;
	}


	public void setMrpPrice(double mrpPrice) {
		this.mrpPrice = mrpPrice;
	}


	public long getQuantity() {
		return quantity;
	}


	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}


	public Category getCategory() {
		return category;
	}


	public void setCategory(Category category) {
		this.category = category;
	}


	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemname=" + itemname + ", image=" + image + ", description=" + description
				+ ", mrpPrice=" + mrpPrice + ", quantity=" + quantity + "]";
	} 

	
}