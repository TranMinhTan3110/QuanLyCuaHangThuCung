package model;

import java.util.Date;

public class Order {
    private int orderID;
    private User user;  // Nhân viên tạo đơn hàng
    private Customer customer; // Khách hàng
    private double totalPrice;
    private Date orderDate;

    public Order(){

    }
    public Order(int orderID, User user, Customer customer, double totalPrice, Date orderDate ) {
        this.orderID = orderID;
        this.user = user;
        this.customer = customer;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;

    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
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
