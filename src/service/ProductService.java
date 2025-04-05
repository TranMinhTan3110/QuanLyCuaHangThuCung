package service;

import dao.DaoInterface;

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
}
