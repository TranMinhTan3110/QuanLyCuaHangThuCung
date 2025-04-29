package service;

import dao.DaoInterface;

import dao.ProductDAO;
import model.entity.Product;
import model.entity.User;

import java.util.ArrayList;

public class ProductService {
    private DaoInterface daoProduct;

    // Constructor nhận vào một repository để dễ dàng thay đổi hoặc kiểm thử
    public ProductService(DaoInterface productRepo) {
        this.daoProduct = productRepo;
    }

    // Lấy danh sách product
    public ArrayList<Product> getAll() {
        return daoProduct.getAll();
    }

    // Thêm user mới
    public boolean insert(Product product) {
        return daoProduct.insert(product);
    }

    // Cập nhật thông tin user
    public boolean update(Product product) {
        return daoProduct.update(product);
    }

    // Xóa user
    public boolean delete(Product product) {
        return daoProduct.delete(product);
    }

    public Product selectByID(int productID) {
        return (Product) daoProduct.selectByID(productID);
    }

    public ArrayList<Product> searchByName(String name) {
        if (daoProduct instanceof ProductDAO) {
            return ((ProductDAO) daoProduct).selectByNameLike(name);
        }
        return new ArrayList<>();
    }

    // Tìm kiếm chính xác (nếu bạn cần thêm)
    public Product searchExactByName(String name) {
        return (Product) daoProduct.selectByName(name);
    }
}
