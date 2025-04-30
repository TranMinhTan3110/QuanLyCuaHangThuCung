package model.entity;

public class Product {
    private int productID;
    private String name;
    private double price;
    private int quantity;
    private Category category;

    // Constructor không tham số
    public Product() {};

    // Constructor đầy đủ
    public Product(int productID, String name, double price, int quantity, Category category) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    // Chuyển sang Object[] để dễ add vào JTable
    public Object[] toObject() {
        return new Object[] {
                productID,
                name,
                price,
                quantity,
                category != null ? category.getCategoryName() : ""
        };
    }

    @Override
    public String toString() {
        return name + " (" + price + "đ, SL: " + quantity + ")";
    }
}
