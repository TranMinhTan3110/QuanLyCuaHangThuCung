package model.entity;

import java.math.BigDecimal;

public class OrderDetail {
	private int orderDetailID;
	private int orderID;
	private Integer productID; // Có thể null nếu là pet
	private Integer petID; // Có thể null nếu là product
	private int quantity;
	private BigDecimal price;

	public OrderDetail() {
	}

	// Constructor không có orderDetailID (dùng khi thêm mới)
	public OrderDetail(int orderID, Integer productID, Integer petID, int quantity, BigDecimal price) {
		this.orderID = orderID;
		this.productID = productID;
		this.petID = petID;
		this.quantity = quantity;
		this.price = price;
	}

	// Constructor đầy đủ (dùng khi đọc từ DB)
	public OrderDetail(int orderDetailID, int orderID, Integer productID, Integer petID, int quantity,
			BigDecimal price) {
		this.orderDetailID = orderDetailID;
		this.orderID = orderID;
		this.productID = productID;
		this.petID = petID;
		this.quantity = quantity;
		this.price = price;
	}

	// Getters & Setters
	public int getOrderDetailID() {
		return orderDetailID;
	}

	public void setOrderDetailID(int orderDetailID) {
		this.orderDetailID = orderDetailID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getPetID() {
		return petID;
	}

	public void setPetID(Integer petID) {
		this.petID = petID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
