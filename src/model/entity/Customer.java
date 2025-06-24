package model.entity;

public class Customer extends Person {
	private int loyaltyPoints = 0;
	private String membershipLevel;
	private String status = "hoạt động";
	// Constructor mặc định
	public Customer() {
	}

	public Customer(int id, String name, String phone, String address, int loyaltyPoints, String membershipLevel, String status) {
		super(id, name, phone, address);
		this.loyaltyPoints = loyaltyPoints;
		this.membershipLevel = membershipLevel;
		this.status = status;
	}

	public Customer(int id, String name, String phone, String address) {
		super(id, name, phone, address);
	}
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public String getMembershipLevel() {
		return membershipLevel;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints += loyaltyPoints;
	}

	public void setMembershipLevel(String membershipLevel) {
		this.membershipLevel = membershipLevel;
	}

	@Override
	public String toString() {
		return "Customer{id=" + getId() + ", name='" + getName() + "', loyaltyPoints=" + loyaltyPoints
				+ ", membershipLevel='" + membershipLevel + "'}";
	}
}
