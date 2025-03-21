package model.entity;

public class OrderDetail {
    private int orderDetailID;  // Mã chi tiết đơn hàng
    private int orderID;        // Mã đơn hàng (liên kết với Order)
    private int productID;      // Mã sản phẩm (liên kết với Product)
    private int quantity;       // Số lượng sản phẩm
    private double price;       // Giá tại thời điểm mua

    public OrderDetail(){

    }
    public OrderDetail(int orderDetailID, int orderID, int productID, int quantity, double price) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.productID = productID;//mã sản phẩm hoặc thú cưng
        this.quantity = quantity;
        this.price = price;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
