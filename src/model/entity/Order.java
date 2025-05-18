package model.entity;

import java.util.Date;

public class Order {
	private int orderID;
	private int userID; // Nhân viên tạo đơn hàng
	private int customerID; // Khách hàng
	private double totalPrice;
	private Date orderDate;

	public Order() {

	}

	public Order(int orderID, int userID, int customerID, double totalPrice, Date orderDate) {
		this.orderID = orderID;
		this.userID = userID;
		this.customerID = customerID;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;

	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int id) {
		this.userID = id;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int id) {
		this.customerID = id;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
