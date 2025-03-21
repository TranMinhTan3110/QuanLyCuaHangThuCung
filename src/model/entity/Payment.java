package model.entity;

import java.sql.Date;

public class Payment {
    private int paymentID;
    private int orderID;
    private String paymentMethod;//phương thức thanh toán(tiền mặt,qr)
    private double amount;
    private Date paymentTime;//thời gian thanh toán

    public Payment(){

    }

    public Payment(int paymentID, int orderID, double amount, String paymentMethod, Date paymentTime) {
        this.paymentID = paymentID;
        this.orderID = orderID;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentTime = paymentTime;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
