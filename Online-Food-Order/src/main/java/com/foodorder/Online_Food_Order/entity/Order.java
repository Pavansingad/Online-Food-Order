package com.foodorder.Online_Food_Order.entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;




	@Entity
	@Table(name = "order_table")
	@SequenceGenerator(name = "generator6", sequenceName = "gen", initialValue = 500)

	public class Order {
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator6")
		@Column(name = "order_id")
		private long orderId;

		@Column(name = "mrp_price")
		private double mrpPrice;

		@Column(name = "quantity")
		private long quantity;

		@Column(name = "total_price")
		private double totalPrice;

		@Column(name = "order_status")
		private String orderStatus;

		@Column(name = "payment_status")
		private String paymentStatus;

		@Column(name = "ordered_date")
		private Date orderedDate;

		@NotEmpty(message = "Item name is required.")
		@Column(name = "itemname", nullable = false,length=30)
		private String itemname;

		@Column(name = "item_image")
		@NotEmpty
		private String image;

		@ManyToOne(cascade = CascadeType.MERGE)
		@JoinColumn(name = "user_id")
		private User user;
		
		@ManyToMany(cascade=CascadeType.MERGE)
	    private List<Item> item;

		public Order() {

		}

		public long getOrderId() {
			return orderId;
		}

		public void setOrderId(long orderId) {
			this.orderId = orderId;
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

		public double getTotalPrice() {
			return totalPrice;
		}

		public void setTotalPrice(double totalPrice) {
			this.totalPrice = totalPrice;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public String getPaymentStatus() {
			return paymentStatus;
		}

		public void setPaymentStatus(String paymentStatus) {
			this.paymentStatus = paymentStatus;
		}

		public Date getOrderedDate() {
			return orderedDate;
		}

		public void setOrderedDate(Date orderedDate) {
			this.orderedDate = orderedDate;
		}
		

		public String getItemname() {
			return itemname;
		}

		public void setItemname(String itemname) {
			this.itemname = itemname;
		}

		public List<Item> getItem() {
			return item;
		}

		public void setItem(List<Item> item) {
			this.item = item;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
	
		}

		@Override
		public String toString() {
			return "Order [orderId=" + orderId + ", mrpPrice=" + mrpPrice + ", quantity=" + quantity + ", totalPrice="
					+ totalPrice + ", orderStatus=" + orderStatus + ", paymentStatus=" + paymentStatus
					+ ", orderedDate=" + orderedDate  + ", user=" + user+ " ]";
		}
		
		
}
