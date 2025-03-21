package model;

public class Customer {
    private int id;
    private String name;
    private int loyaltyPoints;
    private String membershipLevel;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
        this.loyaltyPoints = 0;
        this.membershipLevel = "Basic";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public String getMembershipLevel() {
        return membershipLevel;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
        updateMembershipLevel();
    }

    private void updateMembershipLevel() {
        if (loyaltyPoints >= 1000) {
            membershipLevel = "Platinum";
        } else if (loyaltyPoints >= 500) {
            membershipLevel = "Gold";
        } else if (loyaltyPoints >= 100) {
            membershipLevel = "Silver";
        } else {
            membershipLevel = "Basic";
        }
    }
}
