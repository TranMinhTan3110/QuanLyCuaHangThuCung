package model.entity;

public class Customer extends Person {
    private int loyaltyPoints = 0;
    private String membershipLevel;

    // Constructor mặc định
    public Customer() {
    }

    public Customer(int id, String name, String phone, String address, int loyaltyPoints, String membershipLevel) {
        super(id, name, phone, address); // Gọi constructor của Person
        this.loyaltyPoints += loyaltyPoints;
        this.membershipLevel = membershipLevel;
    }

    public Customer(int id, String name, String phone, String address) {
        super(id, name, phone, address); // Gọi constructor của Person

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
        return "Customer{id=" + getId() + ", name='" + getName() + "', loyaltyPoints=" + loyaltyPoints +
                ", membershipLevel='" + membershipLevel + "'}";
    }
}
