package model.entity;

public class Product {
    private int productID;
    private String name;
    private double price;
    private int categoryID; // Lưu ID của danh mục thay vì object

    public Product(int productID, String name, double price, int categoryID) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.categoryID = categoryID;
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", categoryID=" + categoryID +
                '}';
    }
}
