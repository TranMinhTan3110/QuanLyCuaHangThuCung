package model.entity;

public class Customer extends Person {
    private int loyaltyPoints;
    private String membershipLevel;

    // Constructor mặc định
    public Customer() {
        this.loyaltyPoints = 0;
        this.membershipLevel = "Basic";
    }

    public Customer(int id, String name, String phone, String address,int loyaltyPoints,String membershipLevel) {
        super(id, name, phone, address); // Gọi constructor của Person
        this.loyaltyPoints  =loyaltyPoints;
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
        this.loyaltyPoints = loyaltyPoints;
    }

    public void setMembershipLevel(String membershipLevel) {
        this.membershipLevel = membershipLevel;
    }

    public void addLoyaltyPoints(int points) {
        if (points > 0) {
            this.loyaltyPoints += points;
            updateMembershipLevel();
        }
    }

    private void updateMembershipLevel() {
        if (loyaltyPoints >= 1000) membershipLevel = "Platinum";
        else if (loyaltyPoints >= 500) membershipLevel = "Gold";
        else if (loyaltyPoints >= 100) membershipLevel = "Silver";
        else membershipLevel = "Basic";
    }

    @Override
    public String toString() {
        return "Customer{id=" + getId() + ", name='" + getName() + "', loyaltyPoints=" + loyaltyPoints +
                ", membershipLevel='" + membershipLevel + "'}";
    }
}
