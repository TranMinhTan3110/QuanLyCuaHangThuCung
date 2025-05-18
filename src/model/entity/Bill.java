package model.entity;

import java.sql.Date;

public class Bill {
	private int billID;
	private int orderID;
	private String billMethod;// phương thức thanh toán(tiền mặt,qr)
	private double amount;
	private Date billTime;// thời gian thanh toán

	public Bill() {

	}

	public Bill(int billID, int orderID, double amount, String billMethod, Date billTime) {
		this.billID = billID;
		this.orderID = orderID;
		this.amount = amount;
		this.billMethod = billMethod;
		this.billTime = billTime;
	}

	public int getbillID() {
		return billID;
	}

	public void setbillID(int billID) {
		this.billID = billID;
	}

	public Date getbillTime() {
		return billTime;
	}

	public void setbillTime(Date billTime) {
		this.billTime = billTime;
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

	public String getbillMethod() {
		return billMethod;
	}

	public void setbillMethod(String billMethod) {
		this.billMethod = billMethod;
	}
}
